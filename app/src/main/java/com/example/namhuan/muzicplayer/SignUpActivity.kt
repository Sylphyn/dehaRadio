package com.example.namhuan.muzicplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var client = OkHttpClient()
        btnToSignIn.setOnClickListener {
            val intent : Intent = Intent(this, LogInActivity::class.java)
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
        fun PASSWORD_PATTERN () : Pattern = Pattern.compile(
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"
        )
        fun String.isValidLenght(): Boolean  = this.length >= 6
        fun String.isValidPassword(): Boolean = this.isNotEmpty() && PASSWORD_PATTERN().matcher(this).matches() && this.isValidLenght()
        fun String.isMatchPassword(): Boolean = this.isNotEmpty() && this.equals(etPassword.text.toString())



        etPassword.validate({etPassword->etPassword.isValidPassword()}, "Password need 6 to 12 character and number, 1 upper case is required")
        etEmail.validate({s->s.isValidEmail()}, "Validate Email is required")
        etRePassword.validate({s->s.isMatchPassword()}, "Password not match")

        btnsgupSignUp.setOnClickListener {
            val intent : Intent = Intent(this, MainActivity::class.java)
            val formBody: RequestBody = FormBody.Builder()
                .add("name", etName.text.toString())
                .add("password", etPassword.text.toString())
                .add("email", etEmail.text.toString())
                .build()
            val request: Request = Request.Builder()
                .url(" http://3.1.50.154:3000/users/create")
                .post(formBody)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        val myResponse: String = response.body()!!.string()
                        val jobject = JSONObject(myResponse)
                        var error:Boolean = jobject.getBoolean("error")
                        if(error == true){
                            var message :String = jobject.getJSONArray("message").toString()
                            runOnUiThread { Toast.makeText(this@SignUpActivity,message, Toast.LENGTH_SHORT).show() }
                        }else{
                            var message:String = jobject.getString("message")
                            runOnUiThread { Toast.makeText(this@SignUpActivity,message, Toast.LENGTH_SHORT).show() }
                            startActivity(intent)
                        }

                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

        })
    }

}
}
