package com.example.myappoimts.io.response

import com.example.myappoimts.model.User

data class Loginreponse (val success:Boolean, val user: User, val jwt:String)

