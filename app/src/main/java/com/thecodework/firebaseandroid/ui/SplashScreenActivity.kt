package com.thecodework.firebaseandroid.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.util.Utils

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializer()
    }

    private fun initializer() {
        Utils.changeStatusBar(this@SplashScreenActivity, R.color.dark_color_shadow_light)
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}