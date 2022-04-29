package com.thecodework.firebaseandroid.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat

class Utils {

    companion object {
        fun changeStatusBar(activity: Activity, color: Int) {
            if (Build.VERSION.SDK_INT >= 21) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = ContextCompat.getColor(activity, color)
            }
        }
    }
}