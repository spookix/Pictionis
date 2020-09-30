package com.androidkotlin.pictionis

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.androidkotlin.pictionis.utils.FirebaseSingleton
import timber.log.Timber

// Classe qui permet de se créer un compte

class Register : AppCompatActivity() {

    val fbAuth: FirebaseAuth = FirebaseSingleton.getAuthInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
    }

    fun createAccount(view: View){
        val emailTextview = findViewById<EditText>(R.id.emailEditText)
        val password1Textview = findViewById<EditText>(R.id.passwordEditText1)
        val password2Textview = findViewById<EditText>(R.id.passwordEditText2)

        val email = emailTextview.text.toString()
        val password1 = password1Textview.text.toString()
        val password2 = password2Textview.text.toString()

        if (email.isEmpty() || password1.isEmpty() || password1 != password2){
            Toast.makeText(this, "Login or Password is empty or Password are different", Toast.LENGTH_SHORT).show()
            return
        }

        fbAuth.createUserWithEmailAndPassword(email, password1)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   Toast.makeText(this,"Created with success! Loading main activity...",Toast.LENGTH_SHORT).show()
                    Timber.e("createUserWithEmail:success")
                    fbAuth.currentUser
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Timber.e("createUserWithEmail:failure")
                    Toast.makeText(this, "Wrong format, or Password must be at least six characters .",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}