package com.example.myappoimts.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappoimts.*
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.set
import com.example.myappoimts.Util.PreferenceHelper.get
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import kotlinx.android.synthetic.main.activity_appoiments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppoimentsActivity : AppCompatActivity() {
    private val apiService:ApiService by lazy {
        ApiService.create()
    }
    private val preferences  by lazy {
        PreferenceHelper.defaultPrefs(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoiments)
        Createappoiments.setOnClickListener {

            val crearcitas=Intent(this,
                CreateAppoimentActivity::class.java)
            startActivity(crearcitas)

        }
        myappoiments.setOnClickListener {
            val myappoimnets=Intent(this,
                MyAppoimentActivity::class.java)
            startActivity(myappoimnets)
        }
         btn_sesionlogout.setOnClickListener {
             perfomlogaut()


             clearSessionPreference()


         }
    }


    private fun perfomlogaut(){
        val jwt= preferences["jwt",""]
       val call= apiService.postlogout("Bearer $jwt ")
        call.enqueue(object:Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
              toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                       clearSessionPreference()
                      val main_activity=Intent(this@AppoimentsActivity, MainActivity::class.java)
                      startActivity(main_activity)
                       finish()
            }
        })


    }
    private fun clearSessionPreference(){
        /*val preference=getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("session",false)
        editor.apply()*/

        preferences["jwt"]=""


        }
}
