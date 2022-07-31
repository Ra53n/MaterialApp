package com.example.materialapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.materialapp.R
import com.example.materialapp.domain.MarsRoverPhotoEntity

class MarsAdapter : RecyclerView.Adapter<MarsAdapter.MarsViewHolder>() {

    private var dataList = listOf<MarsRoverPhotoEntity>()

    fun setData(list: List<MarsRoverPhotoEntity>) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MarsViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.mars_fragment_item, parent, false)
    )

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class MarsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: MarsRoverPhotoEntity) {
            itemView.findViewById<ImageView>(R.id.imageView).load(item.imgSrc)
        }
    }
}