package com.test.keepgardeningproject_seller.API

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.test.keepgardeningproject_seller.MainActivity
import kotlin.math.acos

public class KakaoAPI{
    // 카카오톡 로그인 확인
    public fun CheckLogin(context:Context,mainActivity:MainActivity) {
        var userEmail : String? = null
        // var userNickname : String? = null
        // 카카오톡 로그인 화면 보여주기
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(ContentValues.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                // 사용자 정보를 가져오는 데 실패한 경우
                // 에러 처리
                Toast.makeText(context,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show()
                return@me
            }
            if (user != null) {
                // 사용자 정보를 가져온 경우 email이 등록되어있다면?
                userEmail = user.kakaoAccount?.email
                if(userEmail!=null){
                    val newBundle = Bundle()
                    newBundle.putString("joinUserType","kakao")
                    newBundle.putString("joinUserEmail",userEmail)
                    mainActivity.replaceFragment(MainActivity.JOIN_SELLER_ADD_INFO_FRAGMENT,true,newBundle)
                }

                // 사용자 정보가 data안에 잇다면 바로 로그인

            }
        }
    }
    // 카카오 로그아웃 함수
    public fun LogOut() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                // 로그아웃 실패 시 처리
                Log.e("KakaoAPI", "카카오톡 로그아웃 실패", error)
            } else {
                // 로그아웃 성공 시 처리
                Log.i("KakaoAPI", "카카오톡 로그아웃 성공")
                // 로그아웃 후에 수행할 작업을 여기에 추가할
            }
        }
    }


    // 카카오 로그인 calback함수
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(ContentValues.TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(ContentValues.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }


}
// 카카오톡 로그인
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "f3c086ff0fd88b3fd2d4d6fa7aebe99c")
    }
}