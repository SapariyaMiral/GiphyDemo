package com.giphyapp

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.giphyapp.network.ApiClient

import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltAndroidApp
class MyBaseApp : MultiDexApplication() {

    init {
        instance = this
    }

    companion object {
        var instance: MyBaseApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}