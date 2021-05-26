package com.example.radioy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.radioapp.ui.HomeResponse
import com.example.radioapp.ui.HomeResponseItem
import com.example.radioapp.ui.RecentResponse
import com.example.radioapp.ui.RecentResponseItem
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

/**
 * As both the fragment created on the single activity
 * So used the same view model for both the fragment
 */
class DashboardViewModel(application: Application): AndroidViewModel(application) {

//    this is used for the recent list data
    lateinit var recentTable: MutableList<RecentTable>
    var recentList= arrayListOf<RecentResponseItem>()
    var recentLiveData= MutableLiveData<ArrayList<RecentResponseItem>>()

//    this is used for the current list data
    lateinit var currentTable:MutableList<CurrentTable>
    var currentList= arrayListOf<HomeResponseItem>()
     var currentLiveList= MutableLiveData<ArrayList<HomeResponseItem>>()


// Received the data from db and dispatch to UI via live data
    fun getCurrentDataFrmDb(){
        CoroutineScope(IO).launch {
            currentTable =  RoomDb.getDatabase(getApplication()).currentDao().getCurrentTable()
            Log.e("tablesize",": "+currentTable.size)
           createCurrentListFrmtable()
        }
    }

    fun createCurrentListFrmtable(){
        for (crTable in currentTable){
            crTable.apply {
                currentList.add(HomeResponseItem(album,artist,image_url,link_url
                    ,name,played_at,preview_url,sid))
            }
        }
        Log.e("listMan",": "+currentList.size)
        CoroutineScope(Main).launch {
            currentLiveList.value = currentList
        }
    }

    fun showCurrentListToUI():MutableLiveData<ArrayList<HomeResponseItem>>{
        Log.e("ForUI",": ${currentList.size}")
        return currentLiveList
    }


//    Here API has been used for updating the data
    fun fetchCurrentListFrmApi(){
        RetroService.getRepoInsatance!!.endPoints.getCurrentTrack().enqueue(object :
            Callback<HomeResponse> {
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
//            Display error
            }
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful){
                    currentList.clear()
                    for (item :HomeResponseItem in response.body()!!){
                        currentList.add(item)
                    }
                    Log.e("after response",": ${currentList.size}")
                    currentList.toSet().toList()
                    Log.e("unique",": ${currentList.size}")
                    showCurrentListToUI()

                }else {
//                    Display error
                }
            }
        })

    }


    /**
     * Similarly same task as above defined here as well
     *
     */

    fun getRecentDataFrmDb(){
        CoroutineScope(IO).launch {
            recentTable =  RoomDb.getDatabase(getApplication()).recentDao().getRecent()
            Log.e("tablesize",": "+recentTable.size)
            createRecentListFrmtable()
        }
    }

    fun createRecentListFrmtable(){
        for (rcntTable in recentTable){
            rcntTable.apply {
                recentList.add(
                    RecentResponseItem(album,artist,image_url,link_url
                    ,name,played_at,preview_url,sid)
                )
            }
        }
        Log.e("listMan",": "+recentList.size)
        CoroutineScope(Main).launch {
            recentLiveData.value = recentList
        }

    }

    fun showRecentListToUI():MutableLiveData<ArrayList<RecentResponseItem>>{
        Log.e("ForUI",": ${recentList.size}")
        return recentLiveData
    }

    fun fetchRecentListFrmApi(){
        RetroService.getRepoInsatance!!.endPoints.getRecentTrack().enqueue(object : Callback<RecentResponse> {
            override fun onFailure(call: Call<RecentResponse>, t: Throwable) {
//                Display error
            }
            override fun onResponse(
                call: Call<RecentResponse>,
                response: Response<RecentResponse>
            ) {
                if (response.isSuccessful){
                    recentList.addAll(response.body()?.toList()!!)
                    Log.e("after response",": ${recentList.size}")
                    recentList.toSet().toList()
                    Log.e("unique",": ${recentList.size}")
                }else {
//                    Display error
                }
            }
        })
    }
}