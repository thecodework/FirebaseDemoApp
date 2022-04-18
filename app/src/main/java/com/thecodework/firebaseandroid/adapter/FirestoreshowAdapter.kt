package com.thecodework.firebaseandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.model.ModelDbshow

class FirestoreshowAdapter(
    val context: Context,
    val arrayList: ArrayList<ModelDbshow>
) :
    RecyclerView.Adapter<FirestoreshowAdapter.myholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dbshow, parent, false)
        return myholder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        holder.tvName.text = arrayList[position].name
        holder.tvNumber.text = arrayList[position].number
        holder.tvAddress.text = arrayList[position].address
        holder.tvEmail.text = arrayList[position].email

    }

    class myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
    }
}