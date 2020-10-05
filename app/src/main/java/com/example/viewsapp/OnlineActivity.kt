package com.example.viewsapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.viewsapp.databinding.ActivityOnlineBinding
import com.example.viewsapp.remote.POJO.ArticlesItem
import com.example.viewsapp.remote.RetrofitInterfaces
import com.example.viewsapp.remote.RetrofitService
import com.example.viewsapp.rv.ItemListNewsAdapter
import kotlinx.coroutines.launch
import kotlin.collections.forEach as forEach1

class OnlineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineBinding
    //deklarasikan adapter yang akan digunakan pada recyclerview
    private lateinit var adapterRV: ItemListNewsAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = layoutInflater
        binding = ActivityOnlineBinding.inflate(inflater)

        setContentView(binding.root)
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        Glide.with(this).load(R.drawable.logo).into(binding.imgToolbar)
        adapterRV = ItemListNewsAdapter()

        //atur recyclerview rvNews yang akan di gunakan
//        binding.rvNews.setHasFixedSize(true)
//        binding.rvNews.layoutManager = LinearLayoutManager(this)
//        binding.rvNews.adapter = adapterRV

        binding.rvNews.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@OnlineActivity)
            adapter = adapterRV

        }



        val retrofitService = RetrofitService.buildService(RetrofitInterfaces::class.java)
        lifecycleScope.launch {
            val request = retrofitService.topHeadlines("id")
            if (request.isSuccessful) {
                Toast.makeText(
                    this@OnlineActivity,
                    request.body()?.totalResults.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                request.body()?.articles?.let { data ->

                    //buat sebuah array kosong yang hanya bisa di isi oleh ArticlesItem
                    val dataResult = arrayListOf<ArticlesItem>()

                    //lakukan perulangan pada setiap item dari data
                    data.forEach { item ->
                        //jika item tidak bernilai null maka
                        item?.let{
                            //tambahkan item ke dalam dataResult
                            dataResult.add(it)
                        }
                    }
                    //masukkan dataResult ke dalam adapterRV
                    adapterRV.addData(dataResult)

                    //mengisi kekosongan headline
                    val beritaPertama = data[0]
                    binding.itemHeadline.run {
                        textTitle.text = beritaPertama?.title
                        textDate.text = beritaPertama?.publishedAt
                        beritaPertama?.let {
                            Glide.with(this@OnlineActivity)
                                .load(it.urlToImage)
                                .into(imgHeadline)
                        }
                        root.setOnClickListener {
                            val intent = Intent(it.context, DetailActivity::class.java).apply {
                                putExtra("BERITA_ITEM", beritaPertama)
                            }
                            it.context.startActivity(intent)
                        }
                    }
                }
            } else {
                Log.e("OnlineActivity", request.message())
            }
        }
    }
}