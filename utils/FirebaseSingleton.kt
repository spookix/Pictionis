package com.androidkotlin.pictionis.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseSingleton {
    private val fbAuth = FirebaseAuth.getInstance()
    private val fbDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun getAuthInstance(): FirebaseAuth {
        return fbAuth
    }

    fun getDatabaseInstance(): FirebaseDatabase {
        return fbDatabase
    }

}