package com.giphyapp.ui.giphyui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.giphyapp.databinding.ActivityGiphyMainBinding
import com.giphyapp.utils.LiveDataInternetConnections
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GiphyMainActivity : AppCompatActivity() {
     lateinit var liveDataInternet : LiveDataInternetConnections
     private lateinit var binding: ActivityGiphyMainBinding
     var  isConnectivityavailable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        liveDataInternet = LiveDataInternetConnections(application)

      /*  liveDataInternet.observe(this) { isConnected ->
            if (isConnected) {
                Log.d("connect  ::: ", "==========?")
                isConnectivityavailable=true
            } else {
                Log.d("discconnection ::: ", "==========?")
                isConnectivityavailable=false
            }
        }*/
        binding = ActivityGiphyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binidingPager()
    }
    private fun binidingPager()
    {
        val tabArray = arrayOf(
            "Trending",
            "WishList"
        )

        val adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.offscreenPageLimit= ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.viewPager.adapter=adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabArray[position]

        }.attach()
    }

    class ViewPagerFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return TrendingPaggingFragment()
                1 -> return WishListFragment()
            }
            return TrendingPaggingFragment()
        }
    }


}