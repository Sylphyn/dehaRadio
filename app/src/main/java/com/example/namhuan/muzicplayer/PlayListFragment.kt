package com.example.namhuan.muzicplayer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_music.*
import kotlinx.android.synthetic.main.fragment_play_list.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlayListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlayListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PlayListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle("Playist")

        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("video")
        mDatabaseReference.keepSynced(true)

        val videos = ArrayList<VideoInfo>()
        videos.add(VideoInfo("123345665766","","lollo",""))




        recycleplaylist.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
        recycleplaylist.adapter = CustomAdapter(videos)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


}


