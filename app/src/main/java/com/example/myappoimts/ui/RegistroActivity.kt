package com.example.myappoimts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro.*
import android.content.Intent
import com.example.myappoimts.R
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.set
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.io.response.Loginreponse
import com.example.myappoimts.io.response.SimpleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    private val apiService:ApiService by lazy {
        ApiService.create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        tvGoToLogin.setOnClickListener{
        val login= Intent(this, MainActivity::class.java)
        startActivity(login)
    }
        butonregitser.setOnClickListener {
            registerfunction()
        }

    }
    private fun registerfunction(){
        val email=registeremail.text.toString().trim()
        val nombre=registername.text.toString().trim()
        val password=password.text.toString()
        val passconfi=password_confirmation.text.toString()
      if(email.isEmpty()||nombre.isEmpty()||password.isEmpty()||passconfi.isEmpty())
      {
          toast(getString(R.string.completecampos))
          return
      }
        if(password!=passconfi)
        {
            toast(getString(R.string.confirmeclave))
            return
        }
        val call=apiService.postregisteruser(nombre,email,password,passconfi)
        call.enqueue(object:Callback<Loginreponse>{
            override fun onFailure(call: Call<Loginreponse>, t: Throwable) {
               toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Loginreponse>,response: Response<Loginreponse>) {
             if(response.isSuccessful) {
                 val loginreponse = response.body()
                 if (loginreponse == null) {
                     toast(getString(R.string.Error_ingresar))
                     return
                 }
                 if (loginreponse.success) {
                     createSessionPreference(loginreponse.jwt)
                     toast("Bienvenido ${loginreponse.user.name}")
                     goToMenuActivity()
                 } else {
                     toast(getString(R.string.Usuario_Contrasena_incorrecto))
                 }

             }
                else
             {
                 toast(response.toString())

             }
            }
        })

    }
    private fun goToMenuActivity(){
        val appoiments=Intent(this,
            AppoimentsActivity::class.java)
        startActivity(appoiments)
        // finish()

    }
    private fun createSessionPreference(jwt:String){

        val preferences=
            PreferenceHelper.defaultPrefs(this)
        preferences["jwt"]=jwt

    }

}
