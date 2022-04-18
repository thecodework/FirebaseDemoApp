package com.thecodework.firebaseandroid.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityForgetPassBinding

class ForgetPass_Activity : AppCompatActivity() {
    lateinit var binding: ActivityForgetPassBinding
    lateinit var email: String
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializer()
        setClickListener()
    }

    private fun initializer() {
        auth = FirebaseAuth.getInstance()
        Utils.changeStatusBar(this@ForgetPass_Activity, R.color.lowblue)
    }

    private fun setClickListener() {
        binding.btnSubmit.setOnClickListener(View.OnClickListener {
            email = binding.edEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter email id", Toast.LENGTH_LONG).show()
            } else {
                resetPassword(email)
            }
        })
    }

    private fun resetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Check email id", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }

}