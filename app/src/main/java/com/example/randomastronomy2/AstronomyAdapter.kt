package com.example.randomastronomy2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AstronomyAdapter (private val astronomyImageList: List<String>,
                        private val astronomyDateList: List<String>,
                        private val astronomyTitleList: List<String>): RecyclerView.Adapter<AstronomyAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val astronomyImage: ImageView
        val astronomyDate: TextView
        val astronomyTitle: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            astronomyImage = view.findViewById(R.id.astronomy_image)
            astronomyDate = view.findViewById(R.id.astronomy_date)
            astronomyTitle = view.findViewById(R.id.astronomy_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.astronomy_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = astronomyImageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(astronomyImageList[position])
            .centerCrop()
            .into(holder.astronomyImage)

        holder.astronomyDate.text = astronomyDateList[position]
        holder.astronomyTitle.text = astronomyTitleList[position]


        holder.astronomyImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Astronomy photo in $position position clicked", Toast.LENGTH_SHORT).show()
        }

    }
}