package com.example.namhuan.muzicplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnToSignIn.setOnClickListener {
            val intent : Intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }

}
