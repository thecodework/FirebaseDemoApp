package com.thecodework.firebaseandroid.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityFirestoreBinding

class FirestoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirestoreBinding
    lateinit var name: String
    lateinit var number: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this, R.color.light_background)

    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener(View.OnClickListener {
            name = binding.edName.text.toString()
            number = binding.edNumber.text.toString()
            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(
                    this, "Enter Name & Number",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var hashMap: HashMap<String, String> = HashMap<String, String>()
                hashMap.put("name", name)
                hashMap.put("number", number)
                FirebaseFirestore.getInstance().collection("users")
                    .add(hashMap)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(
                            this, "Save",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this, ShowFirestore_Activity::class.java))
                        binding.edName.text.clear()
                        binding.edNumber.text.clear()
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error adding document", e)
                    }
            }
        })
    }
}