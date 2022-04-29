package com.thecodework.firebaseandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityVerifyOtpBinding
import com.thecodework.firebaseandroid.util.Utils

class VerifyOtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerifyOtpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        auth = FirebaseAuth.getInstance()
        Utils.changeStatusBar(this@VerifyOtpActivity, R.color.dark_color_shadow_light)

    }

    private fun setClickListener() {
        binding.btnVerify.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            val storedVerificationId = intent.getStringExtra("storedVerificationId")
            val otp = binding.edotp.text.trim().toString()
            if (otp.isNotEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, BottomnavActivity::class.java)
                    binding.progress.visibility = View.GONE
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        binding.progress.visibility = View.GONE
                    }
                }
            }
    }
}