package com. example.namhuan.muzicplayer

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


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        btnSignIn.setOnClickListener {
            val intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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
        fun EditText.validate(validator: (String) -> Boolean, messageError: String) {
            this.afterTextChanged {
                this.error = if (validator(it)) null else messageError
            }
        }
        fun String.isValidEmail(): Boolean
                = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        edtEmail.validate({ edtEmail -> edtEmail.isValidEmail() }, "Validate Email is required")



    }




}
