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
import com.example.radioapp.ui.HomeResponseItem
import com.example.radioy.adapters.CurrentAdapter
import kotlinx.android.synthetic.main.fragment_home_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeList.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeList : Fragment() {

    lateinit var homeVm:DashboardViewModel
    lateinit var currentAdapter : CurrentAdapter
    var mutableCurrentList = ArrayList<HomeResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
//        fetch and observe the data
        homeVm.getCurrentDataFrmDb()
        homeVm.showCurrentListToUI().observe(viewLifecycleOwner, Observer {
            Log.e("In fragment",": ${it.size}")
            mutableCurrentList.clear()
            mutableCurrentList.addAll(it)
            currentAdapter.notifyDataSetChanged()
            pull_to_refresh.isRefreshing = false

        })
        pull_to_refresh.setOnRefreshListener {
            Log.e("refresh","refresshing")
            homeVm.fetchCurrentListFrmApi()
        }

    }

    fun init(){
        homeVm = ViewModelProvider(this).get(DashboardViewModel::class.java)

        recycler_current.layoutManager = LinearLayoutManager(activity)
        currentAdapter = activity?.let {
            CurrentAdapter(
                it,
                mutableCurrentList
            )
        }!!
        recycler_current.adapter = currentAdapter
    }
}