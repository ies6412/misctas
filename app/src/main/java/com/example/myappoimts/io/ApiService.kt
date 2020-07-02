package com.example.myappoimts.io

import com.example.myappoimts.model.Doctor
import com.example.myappoimts.model.EspecialidadMedica
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path



interface ApiService {


    //para la especialidad
    @GET("api/especialidad")
    abstract fun getespecialidad():Call<ArrayList<EspecialidadMedica>>
    //para la consulta de los médicos segun la especialidad.
    @GET("api/especialidad/{especialidad}/doctors")
     fun getDoctor(@Path("especialidad")Especialidadid:Int):Call<ArrayList<Doctor>>





  companion  object Factory{
    private const val BASE_URL="http://192.168.1.105:8000/"
    fun create():ApiService{

      val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

      return retrofit.create(ApiService::class.java)

    }



  }
}