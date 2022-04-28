package com.thecodework.firebaseandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.adapter.FirestoreshowAdapter
import com.thecodework.firebaseandroid.databinding.ActivityShowFirestoreBinding
import com.thecodework.firebaseandroid.model.ModelDbshow
import java.util.ArrayList

class ShowFirestore_Activity : AppCompatActivity() {
    lateinit var binding: ActivityShowFirestoreBinding
    var arraylist: ArrayList<ModelDbshow> = ArrayList()
    private lateinit var firestoreshowAdapter: FirestoreshowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
        setClickListener()
    }

    private fun initializer() {
        Utils.changeStatusBar(this, R.color.dark_color_shadow_light)
    }

    private fun setClickListener() {
        binding.btnShow.setOnClickListener(View.OnClickListener {
            FirebaseFirestore.getInstance().collection("users")
                .addSnapshotListener { value, e ->
                    arraylist.clear()
                    for (doc in value!!) {
                        val model = doc.toObject(ModelDbshow::class.java)
                        arraylist.add(model)
                    }
                    firestoreshowAdapter =
                        FirestoreshowAdapter(this@ShowFirestore_Activity, arraylist)
                    firestoreshowAdapter.notifyDataSetChanged()
                    binding.rvList.adapter = firestoreshowAdapter
                }


        })
    }
}