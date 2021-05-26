package com.example.radioapp.arch.remote

import com.example.radioapp.ui.HomeResponse
import com.example.radioapp.ui.RecentResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Callbacks defined here for the http calls
 */
interface EndPoints {

    @GET("testapi")
    fun getCurrentTrack(): Call<HomeResponse>

    /*
    However api were same but still created for the brevity
     */
    @GET("testapi")
    fun getRecentTrack(): Call<RecentResponse>

}