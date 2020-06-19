package com.example.myappoimts

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_create_appoiment.*
import java.util.*

class CreateAppoimentActivity : AppCompatActivity() {
    private val selectedcalendar= Calendar.getInstance()
    private var selectedradiobutton: RadioButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appoiment)
        btn_siguiente.setOnClickListener {
            stepone.visibility=View.GONE
            steptwo.visibility= View.VISIBLE
            btn_siguiente.visibility=View.GONE
            btn_confirmar.visibility= View.VISIBLE
        }
        btn_confirmar.setOnClickListener {
            Toast.makeText(this,"cita registrada",Toast.LENGTH_SHORT).show()
            finish()
        }
        val options= arrayOf("Odontologia","Pediatia","Cirujia")
        especialidad.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,options)

        val optiondoctor= arrayOf("medico A","medicoa B","Medico c")
        doctors.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,optiondoctor)
    }


 @SuppressLint("StringFormatMatches")
 @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
 fun onclickfechacitas(v:View){


     val year=selectedcalendar.get(Calendar.YEAR)
     val month=selectedcalendar.get(Calendar.MONTH)
     val dayOfMonth=selectedcalendar.get(Calendar.DAY_OF_MONTH)
     val listener=DatePickerDialog.OnDateSetListener{
             dataPicker,y,m,d->
         //Toast.makeText(this,"$y-$m-$d",Toast.LENGTH_SHORT).show()
         selectedcalendar.set(y,m,d)
         fechacita.setText(resources.getString(
             R.string.app_fecha_calendario,y,
             m.twoDigit(),
             d.twoDigit()
         )
         )
         displayradiobutons()
     }

       val datePickerDialog=DatePickerDialog(this,listener,year,month,dayOfMonth)
     val datepicker =datePickerDialog.datePicker
     val calendar=Calendar.getInstance()
     //limite inferior
     calendar.add(Calendar.DAY_OF_MONTH,1)
     datepicker.minDate=calendar.timeInMillis
     //limite mayor
     calendar.add(Calendar.DAY_OF_MONTH,29)

     datepicker.maxDate=calendar.timeInMillis

     //mostramos
         datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun displayradiobutons(){
        radiogroupleft.removeAllViews()
        radiogrouprigth.removeAllViews()

        selectedradiobutton=null


        var goToLeft=true
       // radiogroup.clearCheck()
        //radiogroup.removeAllViews()
        val hours= arrayOf("3:00 PM","3:30 PM","4:00 PM","4:30 PM")
        hours.forEach {

            val radiobutton = RadioButton(this)

            radiobutton.id = View.generateViewId()
            radiobutton.text = it
             radiobutton.setOnClickListener { view->
                 selectedradiobutton?.isChecked=false
                 selectedradiobutton= view as RadioButton?
                 selectedradiobutton?.isChecked=true


             }



            if(goToLeft)
                radiogroupleft.addView(radiobutton)
            else
                radiogrouprigth.addView(radiobutton)
            goToLeft=!goToLeft

        }
        //radiogroup.checkedRadioButtonId
    }

    fun Int.twoDigit()
        = if(this>9) this.toString() else "0$this"


}
