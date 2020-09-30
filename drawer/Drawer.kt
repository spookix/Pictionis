package com.androidkotlin.pictionis.drawer

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidkotlin.pictionis.R
import com.androidkotlin.pictionis.drawer.PaintView.DEFAULT_BG_COLOR
import com.androidkotlin.pictionis.drawer.PaintView.DEFAULT_COLOR
import com.androidkotlin.pictionis.entities.Picture
import com.androidkotlin.pictionis.utils.FirebaseSingleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.drawer_layout.*
import java.io.ByteArrayOutputStream

class Drawer : AppCompatActivity() {

    lateinit var paintView: PaintView
    var color: Int = DEFAULT_COLOR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        paintView = findViewById(R.id.painView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics);
        paintView.init(metrics)


        buttonSavePicture.setOnClickListener {
            Toast.makeText(this, "Send a image", Toast.LENGTH_SHORT).show()
            val bitmap: Bitmap = paintView.bitmap
            Toast.makeText(this, "Height = ${bitmap.height}" +
                    ", Width = ${bitmap.width} ", Toast.LENGTH_SHORT).show()
            sendPicture(Picture(0,bitmap))

        }

        buttonEraserPicture.setOnClickListener {
            Toast.makeText(this, "Eraser activated", Toast.LENGTH_SHORT).show()
            paintView.color(DEFAULT_BG_COLOR)
        }

        buttonPencilPicture.setOnClickListener {
            Toast.makeText(this, "Pencil activated", Toast.LENGTH_SHORT).show()
            paintView.color(color)
        }

        buttonYellow.setOnClickListener {
            Toast.makeText(this, "Color yellow", Toast.LENGTH_SHORT).show()
            color = Color.YELLOW
            paintView.color(color)
        }

        buttonRed.setOnClickListener {
            Toast.makeText(this, "Color yellow", Toast.LENGTH_SHORT).show()
            color = Color.RED
            paintView.color(color)
        }

        buttonBlack.setOnClickListener {
            Toast.makeText(this, "Color black", Toast.LENGTH_SHORT).show()
            color = Color.BLACK
            paintView.color(color)
        }

        buttonGreen.setOnClickListener {
            Toast.makeText(this, "Color green", Toast.LENGTH_SHORT).show()
            color = Color.GREEN
            paintView.color(color)
        }

        buttonBlue.setOnClickListener {
            Toast.makeText(this, "Color blue", Toast.LENGTH_SHORT).show()
            color = Color.BLUE
            paintView.color(color)
        }

        buttonCyan.setOnClickListener {
            Toast.makeText(this, "Color cyan", Toast.LENGTH_SHORT).show()
            color = Color.CYAN
            paintView.color(color)
        }

        buttonGray.setOnClickListener {
            Toast.makeText(this, "Color gray", Toast.LENGTH_SHORT).show()
            color = Color.GRAY
            paintView.color(color)
        }

        buttonLiteGray.setOnClickListener {
            Toast.makeText(this, "Color lite gray", Toast.LENGTH_SHORT).show()
            color = Color.LTGRAY
            paintView.color(color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.normal -> {
                paintView.normal()
                return true
            }

            R.id.emboss -> {
                paintView.emboss()
                return true
            }

            R.id.blur -> {
                paintView.blur()
                return true
            }
            R.id.clear -> {
                paintView.clear()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendPicture(picture: Picture) {
        val that = this
        val storageInstance = FirebaseStorage.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid!!
        // Create a storage reference from our storage service
        val storageRef: StorageReference = storageInstance.getReference()
            .child("pictures")
            .child(uid)
            .child("picture.jpg")

        val baos = ByteArrayOutputStream()
        val bitmap:Bitmap = picture.bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        val data:ByteArray = baos.toByteArray()
        val uploadTask: UploadTask = storageRef.putBytes(data)
        uploadTask
            .addOnFailureListener { Toast.makeText(that, "Envoi échoué", Toast.LENGTH_SHORT).show() }
            .addOnSuccessListener { Toast.makeText(that, "Envoi réussi", Toast.LENGTH_SHORT).show() }
    }
}