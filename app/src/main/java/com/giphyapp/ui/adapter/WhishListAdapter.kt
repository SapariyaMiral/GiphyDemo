package com.giphyapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giphyapp.MyBaseApp
import com.giphyapp.R
import com.giphyapp.data.database.AppDatabase
import com.giphyapp.data.database.WhishlistBean
import com.giphyapp.databinding.RowGridBinding
import com.giphyapp.utils.DialogUtils
import com.giphyapp.utils.extensions.loadImage
import javax.inject.Inject


class WhishListAdapter @Inject
constructor() : RecyclerView.Adapter<WhishListAdapter.WhishListHolder>() {

     var mList: ArrayList<WhishlistBean> = ArrayList()
     var context :Context ?= null

    class WhishListHolder(val binding: RowGridBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dataBean: WhishlistBean) {
            binding.imgWhishList.loadImage(dataBean.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhishListHolder {
        val binding = RowGridBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        context=parent.context

        return WhishListHolder(binding)
    }


    override fun onBindViewHolder(holder: WhishListHolder, position: Int) {
        holder.bind(mList.get(position))
        holder.binding.imgWhishListRemove.setOnClickListener{

          //delete
            context?.let { it1 ->
                DialogUtils.showMaterialAlertDialog(it1,
                    context!!.getString(R.string.dialogTitle),context!!.getString(R.string.dialogMessage),"yes","no",callback = { dialogInterface, i ->
                    if (i == DialogInterface.BUTTON_POSITIVE) {
                        dialogInterface.dismiss()
                        AppDatabase.getInstance(MyBaseApp.applicationContext())?.getAppDao()?.delete(
                            mList.get(position)
                        )
                        // live data list
                        val liveDataUserBean : List<WhishlistBean>? = AppDatabase.getInstance(
                            MyBaseApp.applicationContext()
                        )?.getAppDao()?.allUsers()
                        updateItems(liveDataUserBean as ArrayList<WhishlistBean>?)


                    } else
                        dialogInterface.dismiss()

                })
            };
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateItems(items: ArrayList<WhishlistBean>?) {
        mList = (items ?: emptyList()) as ArrayList<WhishlistBean>
        notifyDataSetChanged()
    }

}
