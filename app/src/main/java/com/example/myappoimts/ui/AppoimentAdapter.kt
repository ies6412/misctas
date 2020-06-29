package com.example.myappoimts.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappoimts.R
import com.example.myappoimts.model.Appoment
import kotlinx.android.synthetic.main.item_appoiment.view.*


class AppoimentAdapter( private val appoiment: ArrayList<Appoment>)
    :RecyclerView.Adapter<AppoimentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       @SuppressLint("SetTextI18n")
       fun bind(appoment:Appoment) = with(itemView){
               TwAppointesId.text= context.getString(R.string.item_appoiment_id,appoment.id)
               TwHoraId.text=context.getString(R.string.item_appoiment_hora,appoment.hora)
               TwFechaId.text=context.getString(R.string.item_appoiment_fecha,appoment.fecha)
               TwMedicoId.text=context.getString(R.string.item_appoiment_medico,appoment.doctorname)


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