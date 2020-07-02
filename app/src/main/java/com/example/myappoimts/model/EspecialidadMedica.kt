package com.example.myappoimts.model

data class EspecialidadMedica(val id:Int, val nombre:String) {
    override fun toString(): String {
        return nombre
    }

}

