package com.codefresher.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.codefresher.firebasetest.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.FieldPosition

class UpdateUserActivity : AppCompatActivity() {
    lateinit var updateUserBinding: ActivityUpdateUserBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUserBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        val view = updateUserBinding.root
        setContentView(view)
        getAndSetData()
        updateUserBinding.buttonUpdateUser.setOnClickListener {
            updateData()
        }
    }

    fun getAndSetData() {
        val name = intent.getStringExtra("name")
        val age = intent.getStringExtra("age")
        val email = intent.getStringExtra("email")

        updateUserBinding.editTextUpdateName.setText(name)
        updateUserBinding.editTextUpdateAge.setText(age)
        updateUserBinding.editTextUpdateEmail.setText(email)
    }

    fun updateData() {
        val updateName = updateUserBinding.editTextUpdateName.text.toString()
        val updateAge = updateUserBinding.editTextUpdateAge.text.toString().toInt()
        val updatEmail = updateUserBinding.editTextUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String, Any>()
        userMap["id"] = userId
        userMap["userName"] = updateName
        userMap["userAge"] = updateAge
        userMap["userEmail"] = updatEmail
        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(applicationContext,"The user has been updated",Toast.LENGTH_LONG).show()
                finish()
            }
        }

    }




}