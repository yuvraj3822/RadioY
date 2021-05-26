package com.example.radioy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.radioapp.ui.HomeResponse
import com.example.radioapp.ui.RecentResponse

class MainActivity : AppCompatActivity() {
    lateinit var vm:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.saveResponseToDb()

        vm.returReponse().observe(this, Observer {
            if (it.equals("success",ignoreCase = true)){
                Log.e("response","Navigate to next")
                startActivity(Intent(this@MainActivity,Dashboard::class.java))
                finish()
            }else if(it.equals("saved",ignoreCase = true)){
                startActivity(Intent(this@MainActivity,Dashboard::class.java))
                finish()
            } else
            {
                Log.e("error","error")
            }
        })
    }
}