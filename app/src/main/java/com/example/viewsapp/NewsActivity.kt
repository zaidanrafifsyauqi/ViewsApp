package com.example.viewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.viewsapp.data.News
import com.example.viewsapp.data.NewsModel
import com.example.viewsapp.databinding.ActivityNewsBinding
import com.example.viewsapp.rv.ItemHeadlineAdapter
import com.example.viewsapp.rv.ItemListNewsAdapter
import kotlinx.android.synthetic.main.item_headline.*

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var adapter: ItemListNewsAdapter
    private lateinit var adapterHorizontal: ItemHeadlineAdapter
    private lateinit var data: List<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //deklarasikan binding
        val inflater = layoutInflater
        binding = ActivityNewsBinding.inflate(inflater)

        setContentView(binding.root)
        //gunakan toolbar buatan sendiri
        setSupportActionBar(binding.homeToolbar)
        //hilangkan judul di toolbar  dengan set false
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //masukkan gambar ke dalam imageview toolbar
        Glide.with(this).load(R.drawable.logo).into(binding.imgToolbar)

        //data
        data = NewsModel.newslist //data berita
        val beritaHeadline = data[0] //data pertama atau index 0
        val beritaLainnya = data.drop(1) //menghilangkan satu data di awal

        //adapter recyclerview vertical
        adapter = ItemListNewsAdapter()
        binding.rvNews.setHasFixedSize(true)
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter

//        adapter.addData(beritaLainnya)



        //recyclerview horizontal
        adapterHorizontal = ItemHeadlineAdapter()
        binding.rvHorizontal.run {
            layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = adapterHorizontal
        }

        adapterHorizontal.addData(beritaLainnya)

//        binding.itemHeadline.textTitle.text = beritaHeadline.title
//        binding.itemHeadline.txtDate.text = beritaHeadline.desc
//        Glide.with(this).load(beritaHeadline.photo).into(binding.itemHeadline.imgHeadline)
        binding.itemHeadline.run {
            textTitle.text = beritaHeadline.title
            textDate.text = beritaHeadline.desc
            Glide.with(this@NewsActivity).load(beritaHeadline.photo).into(imgHeadline)
        }
        




    }
}