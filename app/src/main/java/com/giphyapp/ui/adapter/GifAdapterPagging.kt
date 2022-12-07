package com.giphyapp.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.giphyapp.MyBaseApp
import com.giphyapp.R
import com.giphyapp.data.database.AppDatabase
import com.giphyapp.data.database.WhishlistBean
import com.giphyapp.data.model.DataItem
import com.giphyapp.databinding.RowTrendingBinding
import com.giphyapp.utils.DialogUtils
import com.giphyapp.utils.extensions.loadImage
import javax.inject.Inject


class GifAdapterPagging @Inject
constructor() : PagingDataAdapter<DataItem, GifAdapterPagging.GifHolder>(GifCreator) {

    var context: Context? = null

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        val dataBean = getItem(position)

        // download size img load
        dataBean?.images?.downsized?.url?.let {
            holder.view.imgTrending.loadImage(it)
        }

        dataBean?.isSelected?.let { holder.view.imgWhishList.isChecked = it }
        holder.view.imgWhishList.tag = position


        holder.view.imgWhishList.setOnClickListener {

            Log.d("clickkkkkkkkk :::: ","---------------------");
            val positionTag = holder.view.imgWhishList.tag
            if (AppDatabase.getInstance(MyBaseApp.applicationContext())?.getAppDao()
                    ?.isRecordExistsUserId(dataBean?.images?.downsized?.url) == true
            ) {
                // if already in wishlist than remove from wishlist
                //delete
                context?.let { it1 ->
                    DialogUtils.showMaterialAlertDialog(it1,
                        context!!.getString(R.string.dialogTitle),
                        context!!.getString(R.string.dialogMessage),
                        "yes",
                        "no",
                        callback = { dialogInterface, i ->
                            if (i == DialogInterface.BUTTON_POSITIVE) {
                                dialogInterface.dismiss()
                                dataBean?.images?.downsized?.url?.let { it2 ->
                                    AppDatabase.getInstance(MyBaseApp.applicationContext())
                                        ?.getAppDao()?.deleteByUrl(
                                        it2
                                    )
                                }

                                if (getItem(positionTag as Int)?.isSelected == true) {
                                    getItem(positionTag as Int)?.isSelected = false;
                                    dataBean?.isSelected = false
                                }
                            } else {
                                getItem(positionTag as Int)?.isSelected = true
                                dataBean?.isSelected = true
                                holder.view.imgWhishList.isChecked
                                notifyDataSetChanged()
                                dialogInterface.dismiss()
                            }

                        })
                };
            } else {
                if (getItem(positionTag as Int)?.isSelected == true) {
                    getItem(positionTag)?.isSelected = false;
                    dataBean?.isSelected = false
                } else {
                    getItem(positionTag)?.isSelected = true;
                    dataBean?.isSelected = true
                    // insert  record in room ===>
                    var urlInsert: String? = dataBean?.images?.downsized?.url;
                    urlInsert?.let {
                        WhishlistBean(
                            it
                        )
                    }?.let {
                        AppDatabase.getInstance(MyBaseApp.applicationContext())?.getAppDao()
                            ?.insert(
                                it
                            )
                    }

                }
            }
        }
    }

    override fun onViewRecycled(holder: GifHolder) {
        holder.view.imgWhishList.setChecked(false) // - this line do the trick
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowTrendingBinding.inflate(inflater, parent, false)
        context = parent.context
        return GifHolder(binding)
    }

    class GifHolder(var view: RowTrendingBinding) : RecyclerView.ViewHolder(view.root) {
    }

    object GifCreator : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }



}



