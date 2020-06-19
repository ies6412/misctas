package com.example.myappoimts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappoimts.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_appoiments.*
import kotlinx.android.synthetic.main.activity_create_appoiment.*

class AppoimentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoiments)
        Createappoiments.setOnClickListener {

            val crearcitas=Intent(this,CreateAppoimentActivity::class.java)
            startActivity(crearcitas)

        }
        myappoiments.setOnClickListener {
            val myappoimnets=Intent(this,MyAppoimentActivity::class.java)
            startActivity(myappoimnets)
        }
         btn_sesionlogout.setOnClickListener {
             clearSessionPreference()
             val main_activity=Intent(this,MainActivity::class.java)
             startActivity(main_activity)
             finish()

         }
    }

    private fun clearSessionPreference(){
        /*val preference=getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("session",false)
        editor.apply()*/
        val preferences=PreferenceHelper.defaultPrefs(this)
        preferences["session"]=false


        }
}
