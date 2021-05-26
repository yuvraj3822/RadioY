package com.example.radioy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.radioapp.ui.RecentResponseItem
import com.example.radioy.adapters.RecentListAdapter
import kotlinx.android.synthetic.main.fragment_recent_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecentList.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecentList : Fragment() {

    lateinit var recentVm:DashboardViewModel
    lateinit var recentAdapter : RecentListAdapter
    var mutableRecentList = ArrayList<RecentResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
//        fetch and show the data
        recentVm.getRecentDataFrmDb()
        recentVm.showRecentListToUI().observe(viewLifecycleOwner, Observer {
            Log.e("In fragment",": ${it.size}")
            mutableRecentList.clear()
            mutableRecentList.addAll(it)
            recentAdapter.notifyDataSetChanged()
            recent_pull_refresh.isRefreshing = false
        })
        recent_pull_refresh.setOnRefreshListener {
            Log.e("refresh","refresshing")
            recentVm.fetchRecentListFrmApi()
        }
    }
fun init(){
    recentVm = ViewModelProvider(this).get(DashboardViewModel::class.java)
    recycler_recent.layoutManager = LinearLayoutManager(activity)
    recentAdapter = activity?.let { RecentListAdapter(it,mutableRecentList) }!!
    recycler_recent.adapter = recentAdapter
}
}