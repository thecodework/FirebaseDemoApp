package com.thecodework.firebaseandroid.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.FragmentStudentBinding

class StudentFragment : Fragment() {
    private lateinit var binding: FragmentStudentBinding
    lateinit var name: String
    lateinit var number: String
    lateinit var email: String
    lateinit var address: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentBinding.inflate(inflater, container, false)
        initializer()
        setClickListener()
        return binding.root
    }

    private fun initializer() {

    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener(View.OnClickListener {
            name = binding.edName.text.toString()
            number = binding.edNumber.text.toString()
            address = binding.edAddress.text.toString()
            email = binding.edEmail.text.toString()
            if (name.isEmpty() || number.isEmpty() || address.isEmpty() || email.isEmpty()) {
                Toast.makeText(
                    activity, "Enter All Field",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var hashMap: HashMap<String, String> = HashMap<String, String>()
                hashMap.put("name", name)
                hashMap.put("number", number)
                hashMap.put("address", address)
                hashMap.put("email", email)
                FirebaseDatabase.getInstance().getReference("User").child("Details").push()
                    .setValue(hashMap)
                Toast.makeText(
                    activity, "Save",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(activity, ShowActivity::class.java))
                binding.edName.text.clear()
                binding.edNumber.text.clear()
                binding.edAddress.text.clear()
                binding.edEmail.text.clear()
            }
            //   databaseReference = FirebaseDatabase.getInstance().getReference("User")
            //   databaseReference.child("numbers").push().setValue(1)


        })
    }

}