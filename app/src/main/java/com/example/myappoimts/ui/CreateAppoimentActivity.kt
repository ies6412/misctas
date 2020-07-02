package com.example.myappoimts.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import retrofit2.Callback
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myappoimts.R
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.model.Doctor
import com.example.myappoimts.model.EspecialidadMedica
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_appoiment.*
import kotlinx.android.synthetic.main.card_view1.*
import kotlinx.android.synthetic.main.card_view_step2.*
import kotlinx.android.synthetic.main.card_view_step3.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CreateAppoimentActivity : AppCompatActivity() {
    private val selectedcalendar= Calendar.getInstance()
    private var selectedTimeRadioButton: RadioButton?=null
    private val apiService:ApiService by lazy {
        ApiService.create()
    }




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

            when {
                fechacita.text.toString().isEmpty() -> {
                    fechacita.error=getString(R.string.validate_ppoiment_date)}
                selectedTimeRadioButton==null -> {
                    Snackbar.make(createAppoimentsLinearLeyaout,
                        R.string.validate_ppoiment_time,Snackbar.LENGTH_SHORT).show()

                }
                else -> {
                    stepone.visibility = View.GONE
                    steptwo.visibility = View.GONE
                    datosdecita()
                    steptree.visibility = View.VISIBLE
                }
            }
         }

        btn_confirmar.setOnClickListener {
            Toast.makeText(this,"cita registrada",Toast.LENGTH_SHORT).show()
            finish()
        }


        //opciones de los select


        cargarespecialidades()
        EscojerEspecialidad()

        //val optiondoctor= arrayOf("medico A","medicoa B","Medico c")
        //doctors.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,optiondoctor)
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
         fechacita.error=null
         displayRadiosButtons()
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
    private fun displayRadiosButtons(){
        radiogroupleft.removeAllViews()
        radiogrouprigth.removeAllViews()

        selectedTimeRadioButton=null


        var goToLeft=true
       // radiogroup.clearCheck()
        //radiogroup.removeAllViews()
        val hours= arrayOf("3:00 PM","3:30 PM","4:00 PM","4:30 PM")
        hours.forEach {

            val radiobutton = RadioButton(this)

            radiobutton.id = View.generateViewId()
            radiobutton.text = it
             radiobutton.setOnClickListener { view->
                 selectedTimeRadioButton?.isChecked=false
                 selectedTimeRadioButton= view as RadioButton?
                 selectedTimeRadioButton?.isChecked=true


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
        horaview.text=selectedTimeRadioButton?.text.toString()




    }

    private fun cargarespecialidades(){

     val call: Call<ArrayList<EspecialidadMedica>> =apiService.getespecialidad()

        //Toast.makeText(this, apiService.getespecialidad().toString(),Toast.LENGTH_LONG).show()
       call.enqueue(object:Callback<ArrayList<EspecialidadMedica>>{
            override fun onFailure(call: Call<ArrayList<EspecialidadMedica>>, t: Throwable) {
              Toast.makeText(this@CreateAppoimentActivity,"Ocurrio un problema al cargar los datos"+t.toString(),Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onResponse(call: Call<ArrayList<EspecialidadMedica>>, response: Response<ArrayList<EspecialidadMedica>>)
            {
              if(response.isSuccessful)
               {
                   val especialidadesfor=response.body()

                   especialidad.adapter=ArrayAdapter<EspecialidadMedica>(this@CreateAppoimentActivity,android.R.layout.simple_list_item_1,especialidadesfor!!)
               }
            }
        })

        // val options= arrayOf("Odontologia","Pediatia","Cirujia")


    }
    //funcion paraescuchar cambos en el select de especialdiad
    private fun EscojerEspecialidad(){

        especialidad.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
           override fun onNothingSelected(parent: AdapterView<*>?) {
               TODO("Not yet implemented")
           }

           override fun onItemSelected(adapter: AdapterView<*>?,view: View?,position: Int, id: Long
           ) {

              val especialidadoption: EspecialidadMedica =  adapter?.getItemAtPosition(position)  as EspecialidadMedica
              // Toast.makeText(this@CreateAppoimentActivity,especialidadoption.nombre,Toast.LENGTH_SHORT).show()
               cargardoctores(especialidadoption.id)

           }
       }

    }
    private fun cargardoctores(especialidadid: Int){

       val call= apiService.getDoctor(especialidadid)
        call.enqueue(object:Callback<ArrayList<Doctor>>{
            override fun onFailure(call: Call<ArrayList<Doctor>>, t: Throwable) {
                Toast.makeText(this@CreateAppoimentActivity,getString(R.string.Error_al_cargar_datos_medicos),Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onResponse(call: Call<ArrayList<Doctor>>,response: Response<ArrayList<Doctor>>) {

                if(response.isSuccessful)
                {
                    //Toast.makeText(this@CreateAppoimentActivity,response.toString(),Toast.LENGTH_LONG).show()
                    val medicosfor=response.body()
                   doctors.adapter=ArrayAdapter<Doctor>(this@CreateAppoimentActivity,android.R.layout.simple_list_item_1,medicosfor!!)
                }
            }
        })

    }


}
