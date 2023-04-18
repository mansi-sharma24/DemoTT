package com.example.demott.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.demott.Modal.ChatDetail
import com.example.demott.Modal.Detail
import com.example.demott.R
import com.example.demott.Utils.App
import com.example.demott.Utils.CommonUtils
import com.example.demott.Utils.Constant
import com.example.demott.ViewModel.DemoViewModal
import com.example.demott.databinding.ActivityHomeBinding
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    private lateinit  var binding : ActivityHomeBinding
    private lateinit  var key : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Navigation.findNavController(this,R.id.home_fragment).navigate(R.id.homeFragment)

        clicks();
        SetData()
    }


    private fun SetData() {
        key = App.getAppPreference()?.getString(Constant.AUTH_TOKEN).toString()
        if (key!=null) {
            binding.name.setText(key)
        }
    }



    private fun clicks() {
        binding.logout.setOnClickListener{
            hitLogoutApi()
        }
        binding.chat.setOnClickListener {
            Navigation.findNavController(this,R.id.home_fragment).navigate(R.id.searchFragment)
        }
    }

    private fun hitLogoutApi() {
        DemoViewModal().logoutLive(this,CommonUtils.getUserId().toString()).observe(this) {
            if (it.status==1){
                CommonUtils.xToast(this,it.message)
                App.getAppPreference()?.logout()
                val mainIntent = Intent(this, SplashActivity::class.java)
                startActivity(mainIntent)
            }
            else{
                CommonUtils.xToast(this,it.message)
            }
        }
    }
}