package com.codefresher.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codefresher.firebasetest.databinding.ActivityLoginBinding
import com.codefresher.firebasetest.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    lateinit var signinBinding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = ActivitySigninBinding.inflate(layoutInflater)
        val view = signinBinding.root
        setContentView(view)
    }
}