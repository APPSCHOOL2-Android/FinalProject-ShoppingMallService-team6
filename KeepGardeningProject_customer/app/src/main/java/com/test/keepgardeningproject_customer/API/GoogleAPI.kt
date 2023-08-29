package com.test.keepgardeningproject_customer.API

import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAPI {
    // 구글 회원탈퇴
    fun googleLogOut(context: Context) {
        val googleSignInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 로그아웃 완료 후 처리

            }
        }
    }



}