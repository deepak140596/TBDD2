package com.sapicons.deepak.tbd2.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sapicons.deepak.tbd2.MainActivity
import com.sapicons.deepak.tbd2.R
import es.dmoral.toasty.Toasty
import java.util.*

class SignInActivity : AppCompatActivity() {

    var mAuth : FirebaseAuth? = null
    var RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Sign In"

        mAuth = FirebaseAuth.getInstance()

    }

    private fun startFirebaseAuthUI(){

        var providers: List<AuthUI.IdpConfig>
        providers = Arrays.asList(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build()
        )

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            //var response = IdpResponse.fromResultIntent(data)!!
            finish()

            if(resultCode == Activity.RESULT_OK){
                var user : FirebaseUser?= FirebaseAuth.getInstance().currentUser

                startNextActivity(user)
            }
        }
    }

    fun startNextActivity(user: FirebaseUser? = null){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        var user : FirebaseUser? = mAuth!!.currentUser
        if(user != null)
            startNextActivity(user)
        else
           startFirebaseAuthUI()
    }
}
