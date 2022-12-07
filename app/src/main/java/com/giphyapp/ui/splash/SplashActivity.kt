package com.giphyapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.giphyapp.databinding.ActivityGiphyMainBinding
import com.giphyapp.databinding.ActivitySplashBinding
import com.giphyapp.ui.base.BaseActivity
import com.giphyapp.ui.giphyui.GiphyMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler().postDelayed({

            val intent = Intent(this,GiphyMainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }



}