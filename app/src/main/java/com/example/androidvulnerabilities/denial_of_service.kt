package com.example.androidvulnerabilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidvulnerabilities.databinding.ActivityDenialOfServiceBinding

class denial_of_service : AppCompatActivity() {
    private lateinit var binding: ActivityDenialOfServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDenialOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=getIntent()
        val SL=intent.getStringExtra("db").toString()
        binding.text1.text=SL
    }
}