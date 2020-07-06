package com.example.myappoimts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappoimts.R
import com.example.myappoimts.model.Appoment
import kotlinx.android.synthetic.main.activity_my_appoiment.*

class MyAppoimentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_appoiment)

        val appoiments=ArrayList<Appoment>()
        appoiments.add(Appoment(1,  "prueba","12/12/12" ,"03:00" ))
        appoiments.add(Appoment(2,  "prueba2","12/13/12" ,"04:00" ))
        appoiments.add(Appoment(3,  "prueba3","12/14/12" ,"05:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))
        appoiments.add(Appoment(4,  "prueba4","12/15/12" ,"06:00" ))


        rwAppoiments.layoutManager=LinearLayoutManager(this)
        rwAppoiments.adapter=
            AppoimentAdapter(appoiments)
    }
}
