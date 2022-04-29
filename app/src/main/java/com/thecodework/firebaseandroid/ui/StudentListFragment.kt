package com.thecodework.firebaseandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.thecodework.firebaseandroid.adapter.DatabaseAdapter
import com.thecodework.firebaseandroid.databinding.FragmentStudentListBinding
import com.thecodework.firebaseandroid.model.ModelDbshow
import java.util.ArrayList

class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    lateinit var arraylist: ArrayList<ModelDbshow>
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        initializer()
        return binding.root
    }

    private fun initializer() {
        arraylist = arrayListOf()
        binding.progress.visibility = View.VISIBLE
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
                    binding.rvList.adapter = DatabaseAdapter(requireContext(), arraylist)
                    binding.progress.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "cancel")
            }
        })
    }
}