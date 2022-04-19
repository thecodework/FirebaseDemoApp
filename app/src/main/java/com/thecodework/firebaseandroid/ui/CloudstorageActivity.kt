package com.thecodework.firebaseandroid.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityCloudstorageBinding
import java.io.IOException
import java.util.*

class CloudstorageActivity : AppCompatActivity() {
    lateinit var binding: ActivityCloudstorageBinding
    val PICK_IMAGE_REQUEST = 1
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCloudstorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this, R.color.dark_color_shadow_light)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }

    private fun setClickListener() {
        binding.btnUpload.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST


            )
        })
        binding.btnSave.setOnClickListener(View.OnClickListener {
            binding.progress.visibility = View.VISIBLE
            if (filePath != null) {
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                ref?.putFile(filePath!!)?.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        binding.progress.visibility = View.GONE
                        binding.imageview.setImageResource(R.drawable.ic_launcher_foreground)
                        Toast.makeText(
                            this, "Save",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

                    ?.addOnFailureListener(OnFailureListener { e ->
                        Toast.makeText(
                            this, e.message,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("TAG", e.message.toString())
                    })


            } else {
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.imageview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}