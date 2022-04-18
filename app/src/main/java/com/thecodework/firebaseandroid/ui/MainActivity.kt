package com.thecodework.firebaseandroid.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var emailid: String
    private lateinit var pass: String
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this@MainActivity, R.color.dark_color_shadow_light)
        auth = FirebaseAuth.getInstance()
    }

    private fun setClickListener() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            emailid = binding.edEmail.text.toString()
            pass = binding.edPassword.text.toString()
            if (emailid.isEmpty() || pass.isEmpty() || pass.length < 5) {
                Toast.makeText(
                    this, "Enter Email & Password should be 6 character",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                userLogin(emailid, pass)
            }
        }
        binding.tvForgetPass.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ForgetPass_Activity::class.java))
        })
        binding.tvPhone.setOnClickListener {
            startActivity(Intent(this, SendOtp_Activity::class.java))
        }
    }

    private fun userLogin(emailid: String, pass: String) {
        auth.signInWithEmailAndPassword(emailid, pass)
            .addOnSuccessListener(this) {
                Toast.makeText(
                    this, "LoginSuccessful",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, HomeActivity::class.java)
                binding.progress.visibility = View.GONE
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity,
                    Pair.create<View, String>(binding.imgBottom, "imgLogin")
                )
                startActivity(intent, options.toBundle())
            }
    }
}


