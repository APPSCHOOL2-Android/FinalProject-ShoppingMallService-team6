package com.test.keepgardeningproject_customer.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback


class NaverAPI {
    fun checkNaverLogin(context: Context){
        // 네이버 로그인 SDK 초기화
        NaverIdLoginSDK.initialize(context, "el7gHNDTrFweYrwQdjFT", "Kp1DmQI6qv", "킵 가드닝")

        val oauthLoginCallback = object : OAuthLoginCallback {

            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                Log.e("로그인 성공","로그인 성공")
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)

    }

}