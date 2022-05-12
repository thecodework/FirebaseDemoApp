package com.thecodework.firebaseandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.model.ModelDbshow

class FirestormsAdapter(
    val context: Context,
    private val arrayList: ArrayList<ModelDbshow>
) :
    RecyclerView.Adapter<FirestormsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dbshow, parent, false)
        return Holder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvName.text = arrayList[position].name
        holder.tvNumber.text = arrayList[position].number
        holder.tvAddress.text = arrayList[position].address
        holder.tvEmail.text = arrayList[position].email
        holder.imageProfile.load(arrayList[position].url) {
            crossfade(true)
            placeholder(R.drawable.profile)
            transformations(CircleCropTransformation())
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val imageProfile: ImageView = itemView.findViewById(R.id.imageProfile)
    }
}