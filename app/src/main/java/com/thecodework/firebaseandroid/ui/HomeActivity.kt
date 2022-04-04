package com.thecodework.firebaseandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var name: String
    lateinit var number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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
                FirebaseDatabase.getInstance().getReference("User").child("Details").push()
                    .setValue(hashMap)
                Toast.makeText(
                    this, "Save",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, ShowActivity::class.java))
                binding.edName.text.clear()
                binding.edNumber.text.clear()
            }
            //   databaseReference = FirebaseDatabase.getInstance().getReference("User")
            //   databaseReference.child("numbers").push().setValue(1)


        })
    }
}