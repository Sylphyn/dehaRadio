package com.example.namhuan.muzicplayer

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

class EdittextExt {
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
}