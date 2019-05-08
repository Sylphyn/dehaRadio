package com. example.namhuan.muzicplayer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_log_in.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)



        btnSignUp.setOnClickListener {
            val intent : Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    afterTextChanged.invoke(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            })
        }


        var client = OkHttpClient()


        btnSignIn.setOnClickListener {
            val formBody: RequestBody = FormBody.Builder()
                .add("name", edtName.text.toString())
                .add("password", edtPassword.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(" http://3.1.50.154:3000/users/login")
                .post(formBody)
                .build()
            val intent : Intent = Intent(this, MainActivity::class.java)
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    if (response.isSuccessful){
                        val myResponse: String = response.body()!!.string()
                        val jobject = JSONObject(myResponse)
                        var error:Boolean = jobject.getBoolean("error")
                        var message:String = jobject.getString("message")
                        if (error == false){
                            startActivity(intent)
                        }else{
                            runOnUiThread { Toast.makeText(this@LogInActivity,message,Toast.LENGTH_SHORT).show() }
                        }

                    }
                }

            })

        }






    }




}
