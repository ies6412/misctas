package com.example.myappoimts.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myappoimts.*
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.set
import com.example.myappoimts.Util.PreferenceHelper.get
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_appoiments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppoimentsActivity : AppCompatActivity() {
    private val apiService:ApiService by lazy {
        ApiService.create()
    }
    private val preferences  by lazy {
        PreferenceHelper.defaultPrefs(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoiments)
        val storeToken= intent.getBooleanExtra("store_token",true)
            if(storeToken) {
            storetoken()
        }


      listener()



    }

    private fun listener(){

        //crear citas
        Createappoiments.setOnClickListener {

           verificartelefono(it)
        }

        //mis citas
        myappoiments.setOnClickListener {
            val myappoimnets=Intent(this,
                MyAppoimentActivity::class.java)
            startActivity(myappoimnets)
        }
        //cerrr sesion
        btn_sesionlogout.setOnClickListener {
            perfomlogaut()
            clearSessionPreference()
        }
        //dato del usuario
        btnprofileactivity.setOnClickListener {
            val editprofile=Intent(this,ProfileActivity::class.java)
            startActivity(editprofile)
        }



    }

    private fun verificartelefono(view: View){

        val jwt=preferences["jwt",""]
        val vertelf= apiService.getUser("Bearer $jwt")
        vertelf.enqueue(object:Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
               if(response.isSuccessful)
               {
                  val tel=response.body()
                   if (tel != null) {
                       if(tel.phone.length>=6)
                       {
                        iniciarcrearcitas()
                       }
                       else
                       {
                           Snackbar.make(view,"Por favor registre un teléfono para contiuar... Gracias!!!",Snackbar.LENGTH_LONG).show()
                       }
                   }
               }
            }
        })




    }

     private fun iniciarcrearcitas(){
         val crearcitas=Intent(this,
             CreateAppoimentActivity::class.java)
         startActivity(crearcitas)
     }

    private fun storetoken(){

       val jwt=preferences["jwt",""]
        val autoHeader="Bearer $jwt"


        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this){ instanceIdResult ->
         val deviceToken=instanceIdResult.token
           val call= apiService.posttoken(autoHeader,deviceToken)
            call.enqueue(object:Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                     toast(t.localizedMessage)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                     if(response.isSuccessful) {
                         Log.d(TAG, "token registrado")
                     }
                    else
                     {
                         Log.d(TAG,"Hubo un problema al registrar el token1")
                     }

                }


            })

        }


    }
    private fun perfomlogaut(){
        val jwt= preferences["jwt",""]
       val call= apiService.postlogout("Bearer $jwt ")

        call.enqueue(object:Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
              toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                     //  clearSessionPreference()
                      val main_activity=Intent(this@AppoimentsActivity, MainActivity::class.java)
                      startActivity(main_activity)
                       finish()
            }
        })


    }
   private fun clearSessionPreference(){
        /*val preference=getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("session",false)
        editor.apply()*/

        preferences["jwt"]=""


        }
    companion object{
        private const val TAG="FMservice"
    }
}
