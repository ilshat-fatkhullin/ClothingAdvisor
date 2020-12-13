package com.group5.clothing_advisor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.group5.clothing_advisor.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private var currUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.signIn.setOnClickListener {
            startSignIn()
        }

        binding.signUp.setOnClickListener{
            startSignUp()
        }
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN || requestCode == RC_SIGN_UP) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                currUser = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                if(requestCode == RC_SIGN_UP){
                    Log.e(TAG, "Could not complete registration")
                }
                else {
                    Log.e(TAG, "Could not complete sign in: ${response?.error}")
                }
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

        if (requestCode == RC_SIGN_UP){
            currUser?.sendEmailVerification()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun startSignIn() {
        val intent = createAuthIntent()

        // Create and launch sign-in intent
        startActivityForResult(
            intent,
            RC_SIGN_IN
        )
    }

    private fun startSignUp(){
        val intent = createAuthIntent()
        startActivityForResult(
            intent,
            RC_SIGN_UP
        )
    }

    private fun createAuthIntent(): Intent{
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        return intent
    }

    companion object {
        private const val RC_SIGN_IN = 123
        private const val RC_SIGN_UP = 124
    }
}