package com.example.myappoimts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*
import android.content.Intent
import com.example.myappoimts.R

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        tvGoToLogin.setOnClickListener{
        val login= Intent(this, MainActivity::class.java)
        startActivity(login)
    }
    }
}
