package com.androidkotlin.pictionis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.pictionis.drawer.Drawer
import com.google.firebase.auth.FirebaseAuth

import com.androidkotlin.pictionis.utils.FirebaseSingleton
import com.google.android.gms.tasks.Task

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import timber.log.Timber

class DrawActivity : AppCompatActivity() {

    val connected: Int = 1
    val fbAuth: FirebaseAuth = FirebaseSingleton.getAuthInstance()
    val fbStorage = FirebaseStorage.getInstance()
    val that = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.e("Entrée dans onCreate draw")

        setContentView(R.layout.draw_main)

        if (fbAuth.currentUser == null) {
            startActivityForResult(Intent(this, Login::class.java), connected)
        }

        findViewById<Button>(R.id.button_research).setOnClickListener{
            var intent: Intent  = Intent(this,Play::class.java)
            startActivity(intent)
        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode.equals(connected)) {
            if (resultCode.equals(RESULT_OK)) {
                Toast.makeText(this, "Menu after account created", Toast.LENGTH_SHORT).show()
            } else {
                startActivityForResult(Intent(this, Login::class.java), connected)
            }
        }
    }

    fun disconnect(view: View) {
        FirebaseAuth.getInstance().signOut()
        //fbAuth.signOut()
        finish()
        startActivity(intent)
    }

    fun newGame(view: View) {
        val intent = Intent(this, Drawer::class.java)
        startActivity(intent)
        //setContentView(R.layout.drawer_layout)
    }

    fun sendMessageButton(view: View) {

    }

}
