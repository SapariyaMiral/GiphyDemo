package com.giphyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.giphyapp.data.model.DataItem
import com.giphyapp.network.RetrofitService
import com.giphyapp.ui.paging.TrendingPageSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingGifRepositoryPagging @Inject constructor(
    private val authClient: RetrofitService
) {

    // page size is when we load data how many records from u want  to load again your next page
    fun getTrendingGifSearch( api_key: String, limit: Int , rating : String, query : String ,offset : Int):
            LiveData<PagingData<DataItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                TrendingPageSource(authClient,api_key,limit,rating, query,offset)
            }
            , initialKey = 1
        ).liveData
    }



}