package com.example.myappoimts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.card_view1.*
import kotlinx.android.synthetic.main.card_view_step2.*
import kotlinx.android.synthetic.main.card_view_step3.*
import java.util.*

class CreateAppoimentActivity : AppCompatActivity() {
    private val selectedcalendar= Calendar.getInstance()
    private var selectedradiobutton: RadioButton?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appoiment)
        btn_siguiente.setOnClickListener {
            if(descripcioncard1.text.toString().length<3)
                descripcioncard1.error= getString(R.string.validate_ppoiment_descripcion)
            else{
            stepone.visibility=View.GONE
            steptwo.visibility= View.VISIBLE
            steptree.visibility=View.GONE


                }
        }
        btn_siguiente2.setOnClickListener {

            if(fechacita.text.toString().length==0)
                fechacita.error=getString(R.string.validate_ppoiment_descripcion)
            else {
                stepone.visibility = View.GONE
                steptwo.visibility = View.GONE
                datosdecita()
                steptree.visibility = View.VISIBLE
            }
         }

        btn_confirmar.setOnClickListener {
            Toast.makeText(this,"cita registrada",Toast.LENGTH_SHORT).show()
            finish()
        }


        //opciones de los select
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

    override fun onBackPressed() {

        when {
            steptree.visibility==View.VISIBLE -> {
                steptwo.visibility=View.VISIBLE
                stepone.visibility=View.GONE
                steptree.visibility=View.GONE

            }
            steptwo.visibility==View.VISIBLE -> {

                steptwo.visibility=View.GONE
                steptree.visibility=View.GONE
                stepone.visibility=View.VISIBLE
            }
            stepone.visibility==View.VISIBLE -> {

                val builder=AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_exit_title))
                builder.setMessage(getString(R.string.dialog_exit_message))
                builder.setPositiveButton(getString(R.string.dialog_exit_positive)) { _, _->
                    finish()
                }
                builder.setNegativeButton(getString(R.string.dialog_exit_negative)){ dialog, _->dialog.dismiss()}
                val dialog=builder.create()
                dialog.show()

            }
        }



    }


    private fun datosdecita(){
       descripcionview.text=descripcioncard1.text.toString()
        medicoview.text=doctors.selectedItem.toString()

        especialidadviewc.text=especialidad.selectedItem.toString()
        fechacitaview.text=fechacita.text.toString()

            val selectradiobutton=RadiogroupConsulta.checkedRadioButtonId
           val selectradiotype=RadiogroupConsulta.findViewById<RadioButton>(selectradiobutton)



        tipocitaview.text=selectradiotype.text.toString()
        horaview.text=selectedradiobutton?.text.toString()




    }
}
