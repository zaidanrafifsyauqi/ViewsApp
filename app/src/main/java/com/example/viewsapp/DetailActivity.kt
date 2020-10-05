package com.example.viewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.viewsapp.databinding.ActivityDetailBinding
import com.example.viewsapp.remote.POJO.ArticlesItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private lateinit var dataIntent : ArticlesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val inflater = layoutInflater
        binding = ActivityDetailBinding.inflate(inflater)

        setContentView(binding.root)

        //set Action bar
        setSupportActionBar(binding.homeToolbar)

        //Tampilkan tombol back pada actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //dapatkan data dari intent
        dataIntent = intent.getParcelableExtra("BERITA_ITEM") as ArticlesItem

        //set judul pada ActionBar
        supportActionBar?.title = dataIntent.title

        //isi webview client
        binding.webView.webChromeClient = MyWebChromeClient()
        binding.webView.webViewClient = MyWebViewClient()

        //aktifkan javascript pada webview client
        binding.webView.settings.javaScriptEnabled = true

        //load url "google.co.id"
        binding.webView.loadUrl(dataIntent.url)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    //buat WebChromeClient
    internal class MyWebChromeClient : WebChromeClient()
    //buat WebViewClient
    internal class MyWebViewClient : WebViewClient()



}