package com.thecodework.firebaseandroid.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.FragmentStudentBinding
import java.io.IOException
import java.util.*


class StudentFragment : Fragment() {
    private lateinit var binding: FragmentStudentBinding
    lateinit var name: String
    lateinit var number: String
    lateinit var email: String
    lateinit var address: String
    private val imageRequest = 1
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentBinding.inflate(inflater, container, false)
        initializer()
        setClickListener()
        return binding.root
    }

    private fun initializer() {
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }

    private fun setClickListener() {
        binding.btnSave.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            if (filePath != null) {
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                ref?.putFile(filePath!!)?.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        binding.imageProfile.setImageResource(R.drawable.profile)
                        val imageUrl = it.toString()
                        name = binding.edName.text.toString()
                        number = binding.edNumber.text.toString()
                        address = binding.edAddress.text.toString()
                        email = binding.edEmail.text.toString()
                        if (name.isEmpty() || number.isEmpty() || address.isEmpty() || email.isEmpty()) {
                            Toast.makeText(
                                activity, "Enter All Field",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progress.visibility = View.GONE
                        } else {
                            val hashMap: HashMap<String, String> = HashMap<String, String>()
                            hashMap.put("name", name)
                            hashMap.put("number", number)
                            hashMap.put("address", address)
                            hashMap.put("email", email)
                            hashMap.put("url", imageUrl)
                            FirebaseDatabase.getInstance().getReference("User").child("Details")
                                .push()
                                .setValue(hashMap)
                            Toast.makeText(
                                activity, "Save",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progress.visibility = View.GONE
                            binding.edName.text.clear()
                            binding.edNumber.text.clear()
                            binding.edAddress.text.clear()
                            binding.edEmail.text.clear()
                        }
                        Log.d("TAG", "url$imageUrl")
                    }
                }

                    ?.addOnFailureListener { e ->
                        Toast.makeText(
                            activity, e.message,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("TAG", e.message.toString())
                    }


            } else {
                Toast.makeText(activity, "Please Upload an Image", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.GONE
            }
        }
        binding.tvUpload.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                imageRequest
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequest && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                val contentResolver = requireActivity().contentResolver
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.imageProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}