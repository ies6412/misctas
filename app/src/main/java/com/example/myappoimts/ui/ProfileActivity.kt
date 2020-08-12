package com.example.myappoimts.ui

//import android.support.v7.app.AppCompatActivity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myappoimts.R
import com.example.myappoimts.Util.PreferenceHelper
import com.example.myappoimts.Util.PreferenceHelper.get
import com.example.myappoimts.Util.toast
import com.example.myappoimts.io.ApiService
import com.example.myappoimts.model.User
import kotlinx.android.synthetic.main.activity_appoiments.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Delayed

class ProfileActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val jwt=preferences["jwt",""]
        val call= apiService.getUser("Bearer $jwt")
        call.enqueue(object:Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user= response.body()
                     if(user!=null) {
                        Handler().postDelayed({
                            mostrardatos(user)
                        } ,1000  )

                     }

                }
            }
        })

        btn_ProfileGuardar.setOnClickListener {
           //creams una funcion para guardar datos.
            saveprofile()
        }


    }

    private fun saveprofile(){
        val jwt=preferences["jwt",""]
        val nombre=profilenombres.text.toString()
        val direccion=profiledireccion.text.toString()
        val phone=profiletelefono.text.toString()
        val call=apiService.PostUser("Bearer $jwt",nombre,direccion,phone)
        call.enqueue(object:Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
               if(response.isSuccessful) {
                   toast(getString(R.string.app_save_file_user))
                   finish()
               }
            }
        })


    }
    private fun mostrardatos(user:User){

     progesbar.visibility=View.GONE
     datosprofile.visibility=View.VISIBLE
     profilenombres.setText(user.name)
     profiledireccion.setText(user.direccion)
     profiletelefono.setText(user.phone)


 }


}
