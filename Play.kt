package com.androidkotlin.pictionis

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidkotlin.pictionis.entities.Message
import com.androidkotlin.pictionis.utils.FirebaseSingleton
import com.androidkotlin.pictionis.utils.MessagesTools
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.play_layout.*
import timber.log.Timber


class Play : AppCompatActivity() {

    private val fbStorage = FirebaseStorage.getInstance()

    private val fbAuth: FirebaseAuth = FirebaseSingleton.getAuthInstance()
    private val mDatabase = FirebaseSingleton.getDatabaseInstance()
    private val mMessageReference = mDatabase.getReference("message")

    private lateinit var adapter: MessagesTools

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_layout)

        mMessageReference.removeValue()

        //"pictures/3DQX7pm5s8b8VX4FaEwwIJwBzoh1"
        val pictureStorage = fbStorage.getReference().child("pictures/3DQX7pm5s8b8VX4FaEwwIJwBzoh1")
        val image = pictureStorage.child("picture.jpg")
        GlideApp.with(this)
            .load(pictureStorage)
            .into(findViewById<ImageView>(R.id.picture))

        messageList.layoutManager = LinearLayoutManager(this)
        adapter = MessagesTools(this)
        messageList.adapter = adapter

        buttonSend.setOnClickListener {
            if (message_editText.text.toString().isNotEmpty()) {
                sendData()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }

        mMessageReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.getValue(Message::class.java)
                //value = value
                if (value != null) {
                    runOnUiThread {
                        adapter.addNewMessage(value)
                        messageList.scrollToPosition(adapter.itemCount - 1);
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Erreur pour l'integration du message dans la base")
            }
        })
    }

    private fun sendData() {

        val message = Message(
            fbAuth.currentUser!!.email.toString(),
            message_editText.text.toString()
        )
        mMessageReference.setValue(message)

        message_editText.setText("")
    }

}