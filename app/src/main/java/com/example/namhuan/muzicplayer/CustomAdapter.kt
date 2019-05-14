package com.example.namhuan.muzicplayer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapter(val videoList: ArrayList<VideoInfo>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.music_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
     return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  video:VideoInfo = videoList[position]
        holder.textviewName.text = video.videoTitle
        holder.textviewMessage.text = video.message
    }

    class  ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textviewName = itemView.findViewById(R.id.tvMusicName) as TextView
        val textviewMessage = itemView.findViewById(R.id.tvShowMessage) as TextView
    }
}