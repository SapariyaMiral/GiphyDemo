package com.giphyapp.ui.giphyui

import android.content.res.Configuration
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.giphyapp.MyBaseApp.Companion.applicationContext
import com.giphyapp.data.database.AppDatabase
import com.giphyapp.data.database.WhishlistBean
import com.giphyapp.databinding.FragmentWishListBinding
import com.giphyapp.ui.adapter.WhishListAdapter
import com.giphyapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListFragment :  BaseFragment<FragmentWishListBinding>(){


    override fun getViewBinding() = FragmentWishListBinding.inflate(layoutInflater)

    override fun listeners() {
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible){
            callWhishlist()
        }
    }

     @Inject
    lateinit var whishListAdapter: WhishListAdapter

    fun callWhishlist()
    {
        val liveDataUserBean : List<WhishlistBean>? = AppDatabase.getInstance(applicationContext())?.getAppDao()?.allUsers()


        binding.recyclerView.setHasFixedSize(true)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        }

        binding.recyclerView.adapter = whishListAdapter

        if(liveDataUserBean?.size!! > 0) {
            binding.recyclerView.visibility=View.VISIBLE
            binding.tvNoRecord.visibility= View.GONE
            whishListAdapter.updateItems(liveDataUserBean as ArrayList<WhishlistBean>?)

        }
        else {
            binding.tvNoRecord.visibility = View.VISIBLE
            binding.recyclerView.visibility=View.GONE
        }

    }
    override fun initSetup() {

           }


}