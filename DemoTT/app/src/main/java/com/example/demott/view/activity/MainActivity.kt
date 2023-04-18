package com.example.demott.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.Navigation
import com.example.demott.R
import com.example.demott.Utils.App
import com.example.demott.Utils.CommonUtils
import com.example.demott.Utils.Constant
import com.example.demott.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
   lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}