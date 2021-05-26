package com.example.radioy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.radioapp.ui.HomeResponse
import com.example.radioapp.ui.RecentResponse
import com.example.radioy.local.RoomDb
import com.example.radioy.remote.RetroService
import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.CurrentTable
import com.tmsfalcon.device.tmsfalcon.database.dbKotlin.RecentTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var responseAdded = MutableLiveData<String>()
lateinit var homeResponse:HomeResponse
    lateinit var recentResponse:RecentResponse

    fun getRecentList() {
        RetroService.getRepoInsatance!!.endPoints.getRecentTrack().enqueue(object : Callback<RecentResponse> {
            override fun onFailure(call: Call<RecentResponse>, t: Throwable) {
//                Display error
            }

            override fun onResponse(
                call: Call<RecentResponse>,
                response: Response<RecentResponse>
            ) {
                if (response.isSuccessful){
//                    saveRecentToDb(response.body()!!)
                recentResponse = response.body()!!
                    savetoDb()
                } else {
//                     Display error
                }

            }

        })


    }



    fun getCurrentList() {

        RetroService.getRepoInsatance!!.endPoints.getCurrentTrack().enqueue(object : Callback<HomeResponse> {
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
//            Display error
            }

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful){
//                    savetoDb(response.body()!!)
                   homeResponse = response.body()!!
                    getRecentList()
                }else {
//                    Display error
                }
            }
        })

    }

    fun saveResponseToDb(){
        CoroutineScope(IO).launch {
            var list = RoomDb.getDatabase(getApplication()).currentDao().getCurrentTable()
            if (list.isNotEmpty()){
                CoroutineScope(Main).launch {
                    responseAdded.value = "saved"
                    Log.e("Apicalled",": No already saved")
                }
            }else {
                Log.e("Apicalled",": yes")
                getCurrentList()
            }
        }

    }

    fun savetoDb(){
        var db  = RoomDb.getDatabase(getApplication())
       CoroutineScope(IO).launch {
           homeResponse.forEach {
               db.currentDao().insert(CurrentTable(null,it.album,it.artist,it.image_url,it.link_url
                   ,it.name,it.played_at,it.preview_url,it.sid))
            Log.e("db","adding")
           }
        Log.e("db","added")

          recentResponse.forEach {
              db.recentDao().insert(
                  RecentTable(null,it.album,it.artist,it.image_url,it.link_url
                      ,it.name,it.played_at,it.preview_url,it.sid))
              Log.e("dbRecent","adding")
          }
           CoroutineScope(Main).launch {
               responseAdded.value = "success"
           }


       }
    }


    fun returReponse(): MutableLiveData<String>{
        return responseAdded
    }




}