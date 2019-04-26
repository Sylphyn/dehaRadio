package com.example.namhuan.muzicplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
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





    }

}
