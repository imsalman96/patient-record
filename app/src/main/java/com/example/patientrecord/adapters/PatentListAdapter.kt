package com.example.patientrecord.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.patientrecord.R
import com.example.patientrecord.data.Patient
import com.example.patientrecord.databinding.ItemPatientListBinding
import com.example.patientrecord.utils.ItemClickListener


class PatentListAdapter(
    private val itemClickListener: ItemClickListener<Patient>,
) : RecyclerView.Adapter<PatentListAdapter.MyViewHolder>() {

    private var all = ArrayList<Patient>()
    private var list = ArrayList<Patient>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<Patient>) {
        this.all = list
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: ItemPatientListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPatientListBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvAge.text = item.age.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(item)
        }

        if (item.gender == "Male") {
           holder.binding.imgUser.setImageResource(R.drawable.male)
        }else {
            holder.binding.imgUser.setImageResource(R.drawable.female)
        }
    }

}