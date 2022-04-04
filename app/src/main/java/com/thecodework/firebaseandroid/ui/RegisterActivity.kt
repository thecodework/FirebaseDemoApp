package com.thecodework.firebaseandroid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var emailid: String
    lateinit var pass: String
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this@RegisterActivity, R.color.light_background)
        auth = FirebaseAuth.getInstance()
    }


    private fun setClickListener() {
        binding.tvLogin.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            Toast.makeText(this, "welcome", Toast.LENGTH_LONG).show()
        })
        binding.btnRegister.setOnClickListener(View.OnClickListener {
            Log.d("TAG", "enter field")
            emailid = binding.edEmail.text.toString().trim()
            pass = binding.edPassword.text.toString().trim()
            if (emailid.isEmpty() || pass.isEmpty() || pass.length < 5) {
                Toast.makeText(
                    this, "Enter Email & Password should be 6 character",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                register(emailid, pass)
            }

        })
    }

    private fun register(userEmail: String, userPass: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "success")
                    Toast.makeText(
                        baseContext, "Successfully Registered",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                } else {
                    Log.d("TAG", "failed", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}