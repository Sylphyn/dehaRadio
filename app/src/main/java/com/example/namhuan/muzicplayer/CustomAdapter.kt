package com.example.namhuan.muzicplayer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapter(internal var context:Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    internal var videoList:MutableList<VideoInfo>

    val lastItemId:String? get() = videoList[videoList.size -1].videoID

    fun addAll(newVideo:List<VideoInfo>){
        val init:Int = videoList.size
        videoList.addAll(newVideo)
        notifyItemChanged(init,newVideo.size)
    }

    fun  removeLastVideo(){
        videoList.removeAt(videoList.size - 1)
    }


    init {
        this.videoList = ArrayList()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.music_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
     return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  video:VideoInfo = videoList[position]
        holder.textviewName.text = video.videoTitle
        holder.textviewMessage.text = video.mesage
    }

      class  ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        internal var textviewName:TextView = itemView.findViewById(R.id.tvMusicName) as TextView
        internal var textviewMessage:TextView = itemView.findViewById(R.id.tvShowMessage) as TextView

    }
}