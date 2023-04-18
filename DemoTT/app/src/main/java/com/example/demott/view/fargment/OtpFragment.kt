package com.example.demott.view.fargment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demott.Utils.App
import com.example.demott.Utils.CommonUtils
import com.example.demott.Utils.Constant
import com.example.demott.ViewModel.DemoViewModal
import com.example.demott.databinding.FragmentOtpBinding
import com.example.demott.view.activity.HomeActivity


class OtpFragment : Fragment() {
private lateinit var binding : FragmentOtpBinding
private lateinit  var getNumb : String
private lateinit var getotp : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater,container,false)

        getData()
        clicks()

        return binding.root
    }

    private fun getData() {
        if (arguments!=null){
            getNumb = requireArguments().getString("number","")
            getotp = requireArguments().getString("otp","")
        }
        else{
            CommonUtils.xToast(requireContext(),"Null Bundle")
        }
    }

    private fun clicks() {
        binding.proceedBtn.setOnClickListener{
            if (binding.otpView.otp.toString().isEmpty()){
                CommonUtils.xToast(requireContext(),"Invalid OTP")
            }
            else if (!binding.otpView.otp.toString().equals(getotp)){
                CommonUtils.xToast(requireContext(),"otp not match")
            }
            else{
                hitregisterApi(binding.otpView.otp.toString())
            }
        }
    }

    private fun hitregisterApi(otp : String) {
        Log.d("hitregisterApi", "hitregisterApi: "+ getNumb+","+ getotp)
        DemoViewModal().registerUserLive(requireActivity(),getNumb,getotp,"","").observe(requireActivity()) {
            if (it.status==1){
                CommonUtils.xToast(requireContext(),it.message)
                App.getAppPreference()?.saveString(Constant.AUTH_TOKEN,it.details.auth_token)
                App.getAppPreference()?.saveString(Constant.SESSION,"1")
                val mainIntent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(mainIntent)
            }
            else{
                CommonUtils.xToast(requireContext(),it.message)
            }
        }
    }

}