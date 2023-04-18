package com.example.demott.view.fargment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.demott.R
import com.example.demott.Utils.CommonUtils
import com.example.demott.ViewModel.DemoViewModal
import com.example.demott.databinding.FragmentViewBinding

class NumberFragment : Fragment() {
private lateinit  var binding : FragmentViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBinding.inflate(layoutInflater,container,false)

        clicks()

        return  binding.root
    }

    private fun clicks() {
        binding.proceedBtn.setOnClickListener{
            if (binding.phoneNumber.text.toString().isEmpty()){
                binding.phoneNumber.setError("Enter Number ")
                binding.phoneNumber.requestFocus()
            }
            else{
                hitGenerateApi(binding.phoneNumber.text.toString());
            }
        }
    }

    private fun hitGenerateApi(numb: String) {
        DemoViewModal().generateOtpLive(requireActivity(),numb).observe(requireActivity()) {
            if (it.status == 1) {
                CommonUtils.xToast(requireContext(),it.details.toString())
                val bundle  = Bundle()
                bundle.putString("number",numb)
                bundle.putString("otp",it.details.toString())
                Navigation.findNavController(binding.root).navigate(R.id.action_numberFragment_to_otpFragment,bundle)

            } else {
                CommonUtils.xToast(requireContext(), it.message)
            }
        }
    }

}