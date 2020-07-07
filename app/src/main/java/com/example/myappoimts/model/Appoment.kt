package com.example.myappoimts.model

import com.google.gson.annotations.SerializedName

/*


"id": 11,
        "descripcion": "deese",
        "fecha_cita": "2020-06-30",
        "tipo_cita": "examen",
        "Estado": "Cancelado",
        "created_at": "2020-06-26T05:51:21.000000Z",
        "hora_cita_12": "9:00 AM",
        "especialidad": {
            "id": 5,
            "nombre": "Traumatología"
        },
        "doctor": {
            "id": 2,
            "name": "Doctor"
        }
*/

data class Appoment (val id:Int,
                     val descripcion :String,
                     @SerializedName("fecha_cita") val fecha: String,
                     @SerializedName("tipo_cita") val TipoCita: String,
                     val Estado:String,
                     @SerializedName("created_at") val CreadoEn:String,
                     @SerializedName("hora_cita_12") val Horacita:String,
                     val especialidad:EspecialidadMedica,
                     val doctor:Doctor
)
