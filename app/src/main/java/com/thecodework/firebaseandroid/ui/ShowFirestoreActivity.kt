package com.thecodework.firebaseandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.adapter.FirestormsAdapter
import com.thecodework.firebaseandroid.databinding.ActivityShowFirestoreBinding
import com.thecodework.firebaseandroid.model.ModelDbshow
import com.thecodework.firebaseandroid.util.Utils
import java.util.ArrayList

class ShowFirestoreActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowFirestoreBinding
    var arraylist: ArrayList<ModelDbshow> = ArrayList()
    private lateinit var firestormsAdapter: FirestormsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializer()
    }

    private fun initializer() {
        binding.progress.visibility = View.VISIBLE
        Utils.changeStatusBar(this, R.color.dark_color_shadow_light)
        FirebaseFirestore.getInstance().collection("users")
            .addSnapshotListener { value, e ->
                arraylist.clear()
                for (doc in value!!) {
                    val model = doc.toObject(ModelDbshow::class.java)
                    arraylist.add(model)
                }
                firestormsAdapter =
                    FirestormsAdapter(this@ShowFirestoreActivity, arraylist)
                firestormsAdapter.notifyDataSetChanged()
                binding.rvList.adapter = firestormsAdapter
                binding.progress.visibility = View.GONE
            }
    }

}