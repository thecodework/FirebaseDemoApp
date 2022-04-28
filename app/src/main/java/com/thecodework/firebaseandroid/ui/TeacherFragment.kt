package com.thecodework.firebaseandroid.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.FragmentStudentBinding
import com.thecodework.firebaseandroid.databinding.FragmentTeacherBinding
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class TeacherFragment : Fragment() {
    private lateinit var binding: FragmentTeacherBinding
    lateinit var name: String
    lateinit var number: String
    lateinit var email: String
    lateinit var address: String
    val PICK_IMAGE_REQUEST = 1
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
    ): View? {
        binding = FragmentTeacherBinding.inflate(inflater, container, false)
        initializer()
        setClickListener()
        return binding.root
    }

    private fun initializer() {
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }

    private fun setClickListener() {
        binding.tvUpload.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
        })
        binding.btnSave.setOnClickListener(View.OnClickListener {
            binding.progress.visibility = View.VISIBLE
            if (filePath != null) {
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                ref?.putFile(filePath!!)?.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        binding.progress.visibility = View.GONE
                        binding.imageProfile.setImageResource(R.drawable.ic_launcher_foreground)
                        Toast.makeText(
                            activity, "Save",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }

                    ?.addOnFailureListener(OnFailureListener { e ->
                        Toast.makeText(
                            activity, e.message,
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("TAG", e.message.toString())
                    })


            } else {
                Toast.makeText(activity, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
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
                FirebaseFirestore.getInstance().collection("users")
                    .add(hashMap)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            ContentValues.TAG,
                            "DocumentSnapshot added with ID: ${documentReference.id}"
                        )
                        Toast.makeText(
                            activity, "Save",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(activity, ShowFirestore_Activity::class.java))
                        binding.edName.text.clear()
                        binding.edNumber.text.clear()
                        binding.edAddress.text.clear()
                        binding.edEmail.text.clear()
                    }
                    .addOnFailureListener { e ->
                        Log.d(ContentValues.TAG, "Error adding document", e)
                    }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
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