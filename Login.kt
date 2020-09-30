package com.androidkotlin.pictionis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.androidkotlin.pictionis.utils.FirebaseSingleton
import timber.log.Timber

// Classe qui permet de se connecter

class Login : AppCompatActivity() {

    private val accountCreated: Int = 1
    private val fbAuth: FirebaseAuth = FirebaseSingleton.getAuthInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
    }

    fun createAccount(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivityForResult(intent, accountCreated)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode.equals(accountCreated)) {
            setResult(RESULT_OK)
            finish()
        }
    }

    fun loginFun(view: View){
        val emailLayout = findViewById<EditText>(R.id.emailEditText)
        val passwordLayout = findViewById<EditText>(R.id.passwordEditText)

        val email = emailLayout.text.toString()
        val password = passwordLayout.text.toString()

        if (email.isEmpty() && password.isNotEmpty()){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show()
            return
        } else if (email.isNotEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
            return
        }else if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Email and Password are empty", Toast.LENGTH_SHORT).show()
            return
        }

        fbAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Timber.e("signInWithEmail:success")
                    Toast.makeText(this, "Logged! Loading main activity...",
                        Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Timber.e( "signInWithEmail:failure")
                    Toast.makeText(this, "Wrong email or password",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}