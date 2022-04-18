package com.thecodework.firebaseandroid.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivitySendOtpBinding
import java.util.concurrent.TimeUnit

class SendOtp_Activity : AppCompatActivity() {
    lateinit var binding: ActivitySendOtpBinding
    lateinit var auth: FirebaseAuth
    var number: String = ""
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        auth = FirebaseAuth.getInstance()
        Utils.changeStatusBar(this@SendOtp_Activity, R.color.dark_color_shadow_light)
    }

    private fun setClickListener() {
        binding.btnSendotp.setOnClickListener {
            login()
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                binding.progress.visibility = View.GONE
                finish()
                Log.d(TAG, "onVerificationCompleted Success")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "onVerificationFailed  $e")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(applicationContext, VerifyOtp_Activity::class.java)
                binding.progress.visibility = View.GONE
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login() {
        number = binding.edNumber.text.trim().toString()
        if (number.isNotEmpty() && number.length == 10) {
            number = "+91$number"
            binding.progress.visibility = View.VISIBLE
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d(TAG, "Auth started")
    }
}