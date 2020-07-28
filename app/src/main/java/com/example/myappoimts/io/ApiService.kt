package com.example.myappoimts.io

import com.example.myappoimts.io.response.Loginreponse
import com.example.myappoimts.io.response.SimpleResponse
import com.example.myappoimts.model.Appoment
import com.example.myappoimts.model.Doctor
import com.example.myappoimts.model.EspecialidadMedica
import com.example.myappoimts.model.Shedule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList


interface ApiService {


    //para la especialidad
    @GET("api/especialidad")
    abstract fun getespecialidad():Call<ArrayList<EspecialidadMedica>>
    //para la consulta de los médicos segun la especialidad.
    @GET("api/especialidad/{especialidad}/doctors")
     fun getDoctor(@Path("especialidad")Especialidadid:Int):Call<ArrayList<Doctor>>

    //para los horarios de los dctores segun la fecha
    @GET("api/shedule/hours")
    fun gethoras(@Query("date")date:String,@Query("doctor")doctor:Int):Call<Shedule>


    //para el login
    @POST("api/login")
    fun postlogin(@Query("email") email:String, @Query("password") password: String):Call<Loginreponse>


    @POST("api/logout")
    fun postlogout(@Header("Authorization")autoHeader:String):Call<Void>

    @GET("api/appoiments")
    fun getcitas(@Header("Authorization")autoHeader:String):Call<ArrayList<Appoment>>

    @POST("api/appoiments")
    @Headers("Accept:application/json")
    //las variables de las query deben ser del mismo nombre que se llaman desde laravel...
    fun postsavecita(@Header("Authorization")autoHeader:String,
                     @Query("descripcion")descripcion :String,
                     @Query("especialidads")especialidads:Int,
                     @Query("Doctor") Doctor:Int,
                     @Query("fecha_cita")fecha_cita :String,
                     @Query("interval")interval:String,
                     @Query("tipo_cita")tipo_cita:String
                     ):Call<SimpleResponse>



    @POST("api/register")
    @Headers("Accept:application/json")
    //las variables de las query deben ser del mismo nombre que se llaman desde laravel...
    fun postregisteruser(
                     @Query("name")name :String,
                     @Query("email")email:String,
                     @Query("password")password:String,
                     @Query("passwordconfirmation")passwordconfirmation:String

    ):Call<Loginreponse>


    @POST("api/fcm/token")
    @Headers("Accept:application/json")
    //las variables de las query deben ser del mismo nombre que se llaman desde laravel...
    fun posttoken(
        @Header("Authorization")autoHeader:String,
        @Query("device_token") token :String
    ):Call<Void>







  companion  object Factory{
    private const val BASE_URL="http://192.168.1.110:8000/"
    fun create():ApiService{
      val interceptor=HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
      val cliente=OkHttpClient.Builder().addInterceptor(interceptor).build()
      val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
          .client(cliente)
        .build()

      return retrofit.create(ApiService::class.java)

    }



  }
}