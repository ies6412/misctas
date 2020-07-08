package com.example.myappoimts.ui

import android.annotation.SuppressLint
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myappoimts.R
import com.example.myappoimts.model.Appoment
import kotlinx.android.synthetic.main.item_appoiment.view.*


class AppoimentAdapter
    :RecyclerView.Adapter<AppoimentAdapter.ViewHolder>() {

    var appoiment=ArrayList<Appoment>()
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       @RequiresApi(Build.VERSION_CODES.KITKAT)
       @SuppressLint("SetTextI18n")
       fun bind(appoment:Appoment) = with(itemView){
               TwAppointesId.text= context.getString(R.string.item_appoiment_id,appoment.id)
               TwHoraId.text=context.getString(R.string.item_appoiment_hora,appoment.Horacita)
               TwFechaId.text=context.getString(R.string.item_appoiment_fecha,appoment.fecha)
               TwMedicoId.text=context.getString(R.string.item_appoiment_medico,appoment.doctor.name)
               TwTDeescripcion.text=appoment.descripcion
               TwespecialdiadId.text=appoment.especialidad.nombre
               TwTipoConsulta.text=appoment.TipoCita
               TwEstado.text=appoment.Estado
           Twfechareserva.text="Esta cita se registro el día: ${appoment.CreadoEn} con la siguiente descripción:"

                imgbutton.setOnClickListener{
                        TransitionManager.beginDelayedTransition(parent as ViewGroup,AutoTransition())
                          if(lineardatos.visibility==View.VISIBLE)
                          {
                              lineardatos.visibility=View.GONE
                             imgbutton.setImageResource(R.drawable.ic_expand_more_black_24dp)
                          }
                    else {
                              lineardatos.visibility = View.VISIBLE
                              imgbutton.setImageResource(R.drawable.ic_expand_less_black_24dp)
                          }
                }



           }




    }
     //crear la visra aprotr e xml de item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.item_appoiment,
               parent,
               false
           )
       )


    }

    // devuelve los datos
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val apoiment=appoiment[position]
        holder.bind(apoiment)
    }

//devolver la cantdad d elemtos
    override fun getItemCount() = appoiment.size


}