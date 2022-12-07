package com.giphyapp.network

import com.giphyapp.data.model.ResponseTrendingGIF
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("trending")
    suspend fun getTrending(@Query("api_key") api_key: kotlin.String,
                            @Query("limit") limit : kotlin.Int,
                            @Query("rating") rating: kotlin.String,
                            @Query("offset") offset : Int): Response<ResponseTrendingGIF>

    @GET("search")
    suspend fun getSearch(@Query("api_key") api_key: kotlin.String,
                          @Query("limit") limit : kotlin.Int,
                          @Query("rating") rating: kotlin.String,
                          @Query("q") query : kotlin.String,
                          @Query("offset") offset : Int
    ): Response<ResponseTrendingGIF>


}