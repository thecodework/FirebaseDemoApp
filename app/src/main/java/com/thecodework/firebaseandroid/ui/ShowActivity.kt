package com.thecodework.firebaseandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.adapter.DatashowAdapter
import com.thecodework.firebaseandroid.databinding.ActivityShowBinding
import com.thecodework.firebaseandroid.model.ModelDbshow
import java.util.*

class ShowActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowBinding
    lateinit var arraylist: ArrayList<ModelDbshow>
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this, R.color.light_background)
        arraylist = arrayListOf()
    }

    private fun setClickListener() {
        binding.btnShow.setOnClickListener({
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Details")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("TAG", dataSnapshot.toString())
                    if (dataSnapshot.exists()) {
                        Log.d("TAG", "msg1")
                        arraylist.clear()
                        Log.d("TAG", "msg2")
                        for (snapShot1 in dataSnapshot.children) {
                            Log.d("TAG", snapShot1.toString())
                            val model = snapShot1.getValue(ModelDbshow::class.java)
                            Log.d("TAG", model.toString())
                            arraylist.add(model!!)
                            Log.d("TAG", arraylist.toString())
                        }
                        binding.rvList.adapter = DatashowAdapter(this@ShowActivity, arraylist)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "cancle")
                }
            })
        })
    }
}