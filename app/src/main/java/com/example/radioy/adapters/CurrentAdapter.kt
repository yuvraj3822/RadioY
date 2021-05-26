package com.example.radioy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.radioapp.ui.HomeResponseItem
import com.example.radioy.R
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class CurrentAdapter internal constructor(
    context: Context,
    data: ArrayList<HomeResponseItem>
) : RecyclerView.Adapter<CurrentAdapter.ViewHolder>() {
    private val mData: ArrayList<HomeResponseItem>
    private val mInflater: LayoutInflater
    private var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = mInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the Views in each row
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.itemView.artist_name.text = " - ${mData[position].artist}"
        holder.itemView.album_title.text = mData[position].album
        holder.itemView.track_title.text = mData[position].name
        Glide.with(context).load(mData[position].image_url)
            .placeholder(R.drawable.ic_baseline_audiotrack_24)
            .into(holder.itemView.image)

    }

//    data size
    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
        this.context = context
    }
}