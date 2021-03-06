package com.example.myappoimts.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myappoimts.R
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.get
import com.example.myappoimts.Util.PreferenceHelper.set
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.io.response.Loginreponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val apiservice:ApiService by lazy { ApiService.create() }

    private val snackbar by lazy {Snackbar.make(mainleyaut,
        R.string.app_press_back_again,Snackbar.LENGTH_SHORT)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this){
            instanceIdResult ->
            val devicetoken=instanceIdResult.token
            Log.d(TAG,"$devicetoken")
           toast("{$devicetoken}")
        }
                                               //mode private =0


        val preferences= PreferenceHelper.defaultPrefs(this)

        if (preferences["jwt",""].contains(".")) {
           // Toast.makeText(this,"text",Toast.LENGTH_SHORT).show()
            goToMenuActivity()
        }

        //priero mavidadmos los datos del uusrio y luego creamos una preferencia

        tvGoToRegister.setOnClickListener{
            toast(getString(R.string.api_Complete_los_datos))
            val intent=Intent(this, RegistroActivity::class.java)
            startActivity(intent)
       }




        BtnLogin.setOnClickListener {


          perfomLogin()




        }
    }
    private fun perfomLogin(){

        val email=etemail.text.toString()
        val password=etpassword.text.toString()
        if(email.trim().isEmpty() || password.trim().isEmpty())
        {
            toast(getString(R.string.Email_password_empty))
            return
        }

            else {
            val call = apiservice.postlogin(email, password)

            call.enqueue(object : Callback<Loginreponse> {
                override fun onFailure(call: Call<Loginreponse>, t: Throwable) {

                    if(t.localizedMessage.contains("failed "))
                    {toast("SERVIDOR FUERA DE LINEA ")}




                    // finish()
                }

                override fun onResponse(
                    call: Call<Loginreponse>,
                    response: Response<Loginreponse>
                ) {
                    if (response.isSuccessful) {
                        val loginreponse = response.body()
                        if (loginreponse == null) {
                            toast(getString(R.string.Error_ingresar))
                            return
                        }
                        if (loginreponse.success) {
                            createSessionPreference(loginreponse.jwt)
                            toast("Bienvenido ${loginreponse.user.name}")
                            goToMenuActivity(true)
                        } else {
                            toast(getString(R.string.Usuario_Contrasena_incorrecto))
                        }

                    } else {
                        toast(getString(R.string.Error_ingresar) + response.toString())
                    }

                }
            })

        }
    }

    private fun goToMenuActivity(isUserInput:Boolean=false ){
        val appoiments=Intent(this,AppoimentsActivity::class.java)
         Log.d(TAG, isUserInput.toString())
        if(isUserInput){
            var boleano:Boolean=true
         intent.putExtra("store_token",boleano)


         }
        startActivity(appoiments)
        finish()

    }
   private fun createSessionPreference(jwt:String){

        val preferences=
            PreferenceHelper.defaultPrefs(this)
             preferences["jwt"]=jwt

    }


    override fun onBackPressed() {

        if(snackbar.isShown)
            super.onBackPressed()
        else
            snackbar.show()

    }
    companion object{
        private const val TAG="FMservice"
    }
}


