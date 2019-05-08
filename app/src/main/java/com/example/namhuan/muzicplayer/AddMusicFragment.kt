package com.example.namhuan.muzicplayer


import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_music.*
import kotlinx.android.synthetic.main.fragment_playing.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AddMusicFragment : Fragment() {
    var client = OkHttpClient()
    val API_KEY: String = "AIzaSyCKPnEIp8dZGkEMSzK_3b61xkgVeCwqHh4"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.setTitle("Add Music")
        return inflater.inflate(R.layout.fragment_add_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val request = okhttp3.Request.Builder()
            .url("https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id=zos6P2dnqzg&key=" + API_KEY)
            .addHeader("Accept", "application/json")
            .method("GET", null)
            .build()


        btnCreate.setOnClickListener {
            var youtubLink: String = edtLink.text.toString()
            var message:String = edtMessage.text.toString()
            var id:String = youtubLink.substring(32,youtubLink.length)
            Toast.makeText(context,id,Toast.LENGTH_SHORT).show()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    if (response.isSuccessful) {
                        val myResponse: String = response.body()!!.string()
                        val jobject = JSONObject(myResponse)
                        var items: String = jobject.getJSONArray("items").toString()

                    activity?.runOnUiThread(Runnable() {
                        edtMessage.setText(items)
                    })


                    }
                }

            })
        }



        super.onActivityCreated(savedInstanceState)
    }
}