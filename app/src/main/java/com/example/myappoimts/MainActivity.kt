package com.example.myappoimts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*
import com.example.myappoimts.PreferenceHelper.get
import com.example.myappoimts.PreferenceHelper.set

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
                                               //mode private =0
       /* val preferences =getSharedPreferences("general", Context.MODE_PRIVATE)
                                //creamos una variable "active:session" y le damos el valor de false
        val session =preferences.getBoolean("session",false)*/

        val preferences=PreferenceHelper.defaultPrefs(this)

        if (preferences["session",false]) {
           // Toast.makeText(this,"text",Toast.LENGTH_SHORT).show()
            goToMenuActivity()
        }

        //priero mavidadmos los datos del uusrio y luego creamos una preferencia

        tvGoToRegister.setOnClickListener{
            val intent=Intent(this,RegistroActivity::class.java)
            startActivity(intent)
       }
        BtnLogin.setOnClickListener {

            Toast.makeText(this,"text",Toast.LENGTH_SHORT).show()
            createSessionPreference()
            goToMenuActivity()




        }
    }
    private fun goToMenuActivity(){
        val appoiments=Intent(this,AppoimentsActivity::class.java)
        startActivity(appoiments)
        finish()

    }
    private fun createSessionPreference(){
       /* val preferences =getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",true)
        editor.apply()*/
        val preferences=PreferenceHelper.defaultPrefs(this)
        preferences["session"]=true

    }
    }


