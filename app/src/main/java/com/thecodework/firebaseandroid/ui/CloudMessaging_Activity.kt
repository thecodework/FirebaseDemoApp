package com.thecodework.firebaseandroid.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.util.Utils


class CloudMessaging_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_messaging)

        Utils.changeStatusBar(this, R.color.dark_color_shadow_light)
        Firebase.messaging.subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

        /* FirebaseInstanceId.getInstance().instanceId
             .addOnCompleteListener(OnCompleteListener { task ->

                 if (!task.isSuccessful) {
                     Log.w(TAG, "getInstanceId failed", task.exception)
                     return@OnCompleteListener
                 }

                 val token = task.result?.token
                 val msg = token.toString()
                 Log.d(TAG, msg)
                 Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
             })*/

    }
}
