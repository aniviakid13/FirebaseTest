package com.codefresher.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.codefresher.firebasetest.databinding.ActivitySigninBinding
import com.codefresher.firebasetest.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var signupBinding: ActivitySignupBinding

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        val view = signupBinding.root
        setContentView(view)

        signupBinding.buttonSignupUser.setOnClickListener {

            val usersEmail = signupBinding.editTextEmailSignin.text.toString()
            val userPassword = signupBinding.editTextPasswordSignin.text.toString()
            signupWithFirebase(usersEmail, userPassword)
        }
    }

    fun signupWithFirebase(userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    applicationContext,
                    "Your account has been created",
                    Toast.LENGTH_LONG
                ).show()
                finish()

            } else {
                Toast.makeText(applicationContext,task.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}