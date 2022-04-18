package com.thecodework.firebaseandroid.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import com.thecodework.firebaseandroid.R

class SplashScreen_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initailizer()
    }

    private fun initailizer() {
        Utils.changeStatusBar(this@SplashScreen_Activity, R.color.dark_color_shadow_light)
        Handler().postDelayed({
            val intent = Intent(this@SplashScreen_Activity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}