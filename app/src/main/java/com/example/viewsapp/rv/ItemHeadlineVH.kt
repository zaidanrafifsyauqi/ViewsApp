package com.example.viewsapp.rv

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewsapp.data.News
import com.example.viewsapp.databinding.ItemHeadlineBinding
import com.google.android.material.card.MaterialCardView

class ItemHeadlineVH(binding: ItemHeadlineBinding) : RecyclerView.ViewHolder(binding.root) {
    private val image : ImageView = binding.imgHeadline
    private val title : TextView = binding.textTitle
    private val date : TextView = binding.textDate
    private val root : MaterialCardView = binding.root

    fun bind(berita: News) {
        title.text = berita.title
        date.text = berita.desc
        Glide.with(root.context).load(berita.photo).into(image)

    }
}