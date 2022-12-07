package com.giphyapp.ui.giphyui

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.giphyapp.data.model.DataItem
import com.giphyapp.data.repository.TrendingGifRepositoryPagging
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TrendingViewModelPagging @Inject constructor(
        private val userRepository: TrendingGifRepositoryPagging
) :  ViewModel() {

    private val  apiKey: String ="6QtytmTXnopmOa2jP20mRoh1rMq98ZQs";
    private val limitPage : Int =25

    fun getTrendingGif(api_key : String =apiKey , limit : Int=limitPage, rating : String ="g" ,  offset : Int=1): LiveData<PagingData<DataItem>> {
        return userRepository.getTrendingGifSearch(api_key,limit,rating,"" ,offset).cachedIn(viewModelScope)
    }

    fun getTrendingGifSearch(api_key : String=apiKey, limit : Int=limitPage, rating : String ="g" ,  query : String ,offset : Int=1): LiveData<PagingData<DataItem>> {
        return userRepository.getTrendingGifSearch(api_key,limit,rating, query,offset).cachedIn(viewModelScope)
    }
}
