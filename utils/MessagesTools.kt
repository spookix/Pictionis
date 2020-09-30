package com.androidkotlin.pictionis.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.pictionis.R
import com.androidkotlin.pictionis.entities.Message
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.message_layout_recieved.view.*
import kotlinx.android.synthetic.main.message_layout_sent.view.*

private const val VIEW_MY_MESSAGE = 1
private const val VIEW_OTHER_MESSAGE = 2

class MessagesTools(val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {

    private val messages: ArrayList<Message> = ArrayList()
    private val fbAuth: FirebaseAuth = FirebaseSingleton.getAuthInstance()

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addNewMessage(message: Message){
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)

        return if(fbAuth.currentUser!!.email == message.user) {
            VIEW_MY_MESSAGE
        }
        else {
            VIEW_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_layout_sent, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.message_layout_recieved, parent, false)
            )
        }
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {

        private var messageText: TextView = view.message_content_sent
        private var userText: TextView = view.email_message_sent

        override fun bind(message: Message) {
            messageText.text = message.message
            userText.text = message.user
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.message_content_received
        private var userText: TextView = view.email_message_received

        override fun bind(message: Message) {
            messageText.text = message.message
            userText.text = message.user
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder.bind(message)
    }
}


open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}