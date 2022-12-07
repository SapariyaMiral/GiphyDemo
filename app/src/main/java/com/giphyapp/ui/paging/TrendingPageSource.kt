package com.giphyapp.ui.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.giphyapp.data.model.DataItem
import com.giphyapp.data.model.ResponseTrendingGIF
import com.giphyapp.network.RetrofitService
import retrofit2.Response
import javax.inject.Inject


class TrendingPageSource @Inject constructor(
    private val service: RetrofitService,
    private val api_key1: String,
    private val limit1: Int,
    private val rating: String,
    private val query : String,
    private var offset: Int
    ) :
    PagingSource<Int, DataItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {

        return try {
            var response : Response<ResponseTrendingGIF>;
            if(query.isEmpty()) {
                //trending page source
                response = service.getTrending(api_key1, limit1, rating, offset)
            }
            else {
                //search page source
                 response = service.getSearch(api_key1, limit1, rating, query, offset)
            }
            val pagedResponse = response.body()
            val data = pagedResponse?.data

            if (pagedResponse?.pagination != null) {
                val uri = Uri.parse(pagedResponse.pagination!!.offset.toString())
                offset = uri.toString()?.toInt() + 1
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = offset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}