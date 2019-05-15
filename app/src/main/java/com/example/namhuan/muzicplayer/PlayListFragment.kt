package com.example.namhuan.muzicplayer

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
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
    var mRecyclerView : RecyclerView? = null
    lateinit var adapter: CustomAdapter

    var isLoading =false
    var isMaxData =false

    val ITEM_COUNT =20
    var total_item = 0
    var last_visible_item = 0

    var lastNode: String = ""
    var lastKey: String = ""



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
        mRecyclerView = view?.findViewById(R.id.recycleplaylist)

        getLastKey()

        val layoutManager = LinearLayoutManager(context)
        recycleplaylist.layoutManager = layoutManager


        adapter = CustomAdapter(this!!.context!!)
        recycleplaylist.adapter = adapter

        getVideo()

        recycleplaylist.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                total_item =layoutManager.itemCount
                last_visible_item = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && total_item <= last_visible_item + ITEM_COUNT){
                    getVideo()
                    isLoading  = true
                }
            }
        })










        super.onActivityCreated(savedInstanceState)
    }

    private fun getVideo() {
        if (!isMaxData){
            val query:Query
            if (TextUtils.isEmpty((lastNode)))
                query = FirebaseDatabase.getInstance().reference
                    .child("video")
                    .orderByKey()
                    .limitToFirst(ITEM_COUNT)
            else
                query = FirebaseDatabase.getInstance().reference
                    .child("video")
                    .startAt(lastNode)
                    .limitToFirst(ITEM_COUNT)

            query.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.hasChildren()){
                        val videoList = ArrayList<VideoInfo>()
                        for (snapshot:DataSnapshot in p0.children)
                            videoList.add(snapshot.getValue(VideoInfo::class.java)!!)
                        lastNode = videoList[videoList.size -1].videoID!!

                        if (!lastNode.equals(lastKey))
                            videoList.removeAt(videoList.size -1)
                        else
                            lastNode = "end"

                        adapter.addAll(videoList)
                        isLoading = false
                    }else{
                        isLoading = false
                        isMaxData = true
                    }
                }

            })
        }
    }

    private fun getLastKey() {
        val get_last_key:Query = FirebaseDatabase.getInstance().reference
            .child("video")
            .orderByKey()
            .limitToLast(1)

        get_last_key.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (videoSnapshot in p0.children)
                    lastKey = videoSnapshot.key!!
            }

        })
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }



}


