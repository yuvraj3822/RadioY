package com.example.radioy.remote

import com.example.radioapp.arch.remote.EndPoints
import com.example.radioy.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton class for creating single instance for retrofit
 * service
 */

class RetroService private constructor() {

    lateinit var endPoints: EndPoints
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Utils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        endPoints = retrofit.create(EndPoints::class.java)
    }

    companion object {
        var retroService: RetroService? = null
        val getRepoInsatance: RetroService?
            get() {
                if (retroService == null) {
                    retroService = RetroService()
                }
                return retroService
            }
    }


}