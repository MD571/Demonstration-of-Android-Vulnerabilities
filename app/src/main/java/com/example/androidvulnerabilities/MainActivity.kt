package com.example.androidvulnerabilities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.androidvulnerabilities.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var security:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        security="low"
        binding.rgroup.setOnCheckedChangeListener{group, i->
            var rb=findViewById<RadioButton>(i)
            Toast.makeText(this,rb.text.toString(),Toast.LENGTH_LONG)
            when(rb.text.toString()){
                "LOW"->{
                    security="low"
                }
                "HIGH"->{
                    security="high"
                }
            }
        }
        if (isNetwork(getApplicationContext())){

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show()

        } else {

            Toast.makeText(getApplicationContext(), "Internet Is Not Connected", Toast.LENGTH_SHORT).show()
        }
        if (SDK_INT >= Build.VERSION_CODES.R) {
            Log.d("myz", "" + SDK_INT)
            if (!Environment.isExternalStorageManager()) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    ), 1
                ) //permission request code is just an int
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
        }/*
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val getpermission = Intent()
                getpermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(getpermission)
            }
        }
        binding.ce.setOnClickListener{
            val i=Intent(this,code_execution::class.java)
            i.putExtra("SL",security)
            startActivity(i)
        }*/
        binding.mc.setOnClickListener{
            val i=Intent(this,memory_corruption::class.java)
            i.putExtra("SL",security)
            startActivity(i)
        }
        binding.si.setOnClickListener{
            val i=Intent(this,sql_injection::class.java)
            i.putExtra("SL",security)
            startActivity(i)
        }
        /*binding.dos.setOnClickListener{
            val i=Intent(this,denial_of_service::class.java)
            i.putExtra("SL",security)
            startActivity(i)
        }*/
        binding.bs.setOnClickListener{
            val i=Intent(this,bypass_something::class.java)
            i.putExtra("SL",security)
            startActivity(i)
        }

    }
    fun isNetwork(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
            true
        } else false
    }
}