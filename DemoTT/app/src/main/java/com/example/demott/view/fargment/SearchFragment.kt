package com.example.demott.view.fargment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.demott.Modal.Detail
import com.example.demott.R
import com.example.demott.Utils.CommonUtils
import com.example.demott.ViewModel.DemoViewModal
import com.example.demott.databinding.FragmentSearchBinding
import com.example.demott.view.adapter.AdapterUsers
import java.io.Serializable

class SearchFragment : Fragment()  {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var arrayUserList: List<Detail>
    private lateinit var adapterUser: AdapterUsers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hitallUseApi()

    }

    private fun hitallUseApi() {
        DemoViewModal().get_all_user(requireActivity(), CommonUtils.getUserId().toString()).observe(requireActivity()){
            if (it.status==1){
                CommonUtils.xToast(requireContext(),it.message)
                arrayUserList = it.details
                adapterUser  = AdapterUsers(arrayUserList,object : AdapterUsers.ClickCallback{
                    override fun clickItem(position: Int) {
                        var bundle =  Bundle()
//                        bundle.putSerializable("myList", arrayUserList as Serializable)         **** SENDING LIST IN BUNDLE *********
//                        bundle.putInt("position", position)

                        bundle.putString("name",arrayUserList.get(position).name)
                        bundle.putString("authtoken",arrayUserList.get(position).auth_token)
                        bundle.putString("img",arrayUserList.get(position).image)
                        Navigation.findNavController(binding.root).navigate(R.id.action_searchFragment_to_chatFragment,bundle)
                    }
                })
                binding.itemRecycler.adapter = adapterUser
            }
            else{
                CommonUtils.xToast(requireContext(),it.message)
            }
        }
    }

}