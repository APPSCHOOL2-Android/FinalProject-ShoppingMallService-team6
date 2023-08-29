package com.test.keepgardeningproject_customer.API

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


public class KakaoAPI {

    fun checkKaKaoLogin(context: Context) {
        // 로그인 조합 예제
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    // 카카오 로그아웃 함수
    fun kakaoLogOut() {
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
    // 카카오톡 회원탈퇴 함수
    fun kakaoWithdraw() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                // 탈퇴 실패 시 처리
                Log.e("KakaoAPI", "카카오톡 계정 탈퇴 실패", error)
            } else {
                // 탈퇴 성공 시 처리
                Log.i("KakaoAPI", "카카오톡 계정 탈퇴 성공")
            }
        }
    }

}

// 카카오톡 로그인
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "ea09086ccd4752a53c2a39654fdcc453")
    }
}