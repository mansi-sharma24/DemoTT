package com.example.demott.view.fargment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.demott.Modal.ChatDetail
import com.example.demott.Modal.Detail
import com.example.demott.R
import com.example.demott.Utils.CommonUtils
import com.example.demott.databinding.FragmentHomeBinding
import com.example.demott.view.adapter.AdapterChats
import com.google.firebase.database.*
import java.io.Serializable

class HomeFragment : Fragment() {
    private lateinit var databaseRef : DatabaseReference
    private  var listOfUserChat : ArrayList<ChatDetail> = ArrayList()
    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapterChat : AdapterChats

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=  FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseRef = FirebaseDatabase.getInstance().getReference("Users")

        setData()
    }

    private fun setData() {
        Log.d("onDataChange", "setData: "+ CommonUtils.getUserId())

        databaseRef.child(CommonUtils.getUserId().toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    listOfUserChat.clear()
                    val university: ChatDetail? = postSnapshot.getValue(ChatDetail::class.java)
                    if (university != null) {
                        listOfUserChat.add(university)
                        if (listOfUserChat.size==0){
                            binding.emptyText.visibility = View.VISIBLE
                        }
                        else{
                            binding.emptyText.visibility = View.GONE

                            adapterChat = AdapterChats(listOfUserChat,object :
                                AdapterChats.ClickCallback{
                                override fun click(position: Int) {
                                    var bundle =  Bundle()
                                    bundle.putString("name",listOfUserChat.get(position).name)
                                    bundle.putString("authtoken",listOfUserChat.get(position).auth_token)
                                    bundle.putString("img",listOfUserChat.get(position).image)
                                    Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_chatFragment,bundle)
                                }
                            })
                            binding.recyclerView.adapter = adapterChat
                        }
                    }
                    else{
                        CommonUtils.xToast(requireContext(),"null model")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                CommonUtils.xToast(requireContext(),error.message)
            }

        })

    }

}