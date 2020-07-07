package com.example.myappoimts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappoimts.R
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.get
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.model.Appoment
import kotlinx.android.synthetic.main.activity_my_appoiment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppoimentActivity : AppCompatActivity() {
    private val apiService:ApiService by lazy {
        ApiService.create()
    }
     private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
     }

    private val appoimentAdapter= AppoimentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_appoiment)

       loadappoiments()

        /*val appoiments=ArrayList<Appoment>()
        appoiments.add(Appoment(1,  "prueba","12/12/12" ,"03:00" ))
        appoiments.add(Appoment(2,  "prueba2","12/13/12" ,"04:00" ))
        appoiments.add(Appoment(3,  "prueba3","12/14/12" ,"05:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))*/


        rwAppoiments.layoutManager=LinearLayoutManager(this)
        rwAppoiments.adapter= appoimentAdapter
    }
    private fun loadappoiments(){
        val jwt=preferences["jwt",""]
      val call= apiService.getcitas("Bearer $jwt")
        call.enqueue(object:Callback<ArrayList<Appoment>>{
            override fun onFailure(call: Call<ArrayList<Appoment>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Appoment>>,
                response: Response<ArrayList<Appoment>>
            ) {
                if(response.isSuccessful) {
                    response.body()?. let {
                        appoimentAdapter.appoiment=it
                        appoimentAdapter.notifyDataSetChanged()
                    }

                }
            }
        })

    }
}
