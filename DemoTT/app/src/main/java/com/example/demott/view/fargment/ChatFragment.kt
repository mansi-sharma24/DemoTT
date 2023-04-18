package com.example.demott.view.fargment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.demott.Modal.ChatDetail
import com.example.demott.R
import com.example.demott.Utils.CommonUtils
import com.example.demott.databinding.FragmentChatBinding
import com.example.demott.view.adapter.ChattingChatAdapter
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {
   private lateinit var binding : FragmentChatBinding
     var position: Int = 0
    private lateinit var databaseRef : DatabaseReference
    private lateinit var senderRef : DatabaseReference
    private lateinit var receiverRef : DatabaseReference
    private lateinit var authtoken : String
    private lateinit var  name : String
    private lateinit var img : String
    private lateinit var chattingAdapter: ChattingChatAdapter

    var  senderList : ArrayList<ChatDetail>  = ArrayList()
    var  receverList : ArrayList<ChatDetail>  = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        senderRef = FirebaseDatabase.getInstance().getReference("Chats")
        receiverRef = FirebaseDatabase.getInstance().getReference("Chats")

        getData();
        clicks()
        checkNodes()

    }

    private fun clicks() {
        binding.send.setOnClickListener{
            if (binding.edt.text.length==0){
                CommonUtils.xToast(requireContext(),"Enter message")
                binding.edt.requestFocus()
            }
            else{
                CreatefirebaseNode()
            }
        }
        binding.themes.setOnClickListener {

        }
    }

    private fun CreatefirebaseNode() {
        val senderkey : String? = senderRef.push().key
        val receiverkey : String? = receiverRef.push().key
        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleDate.format(Date())

        val Senddata  = ChatDetail(name, authtoken, img, binding.edt.text.toString(), "sender",currentDate.substring(10,15),senderkey.toString())
        val Receivedata  = ChatDetail(name,CommonUtils.getUserId().toString(),img,binding.edt.text.toString(),"receiver",currentDate.substring(10,15),receiverkey.toString())
        databaseRef.child(CommonUtils.getUserId().toString()).child(authtoken).setValue(Senddata)
        databaseRef.child(authtoken).child(CommonUtils.getUserId().toString()).setValue(Receivedata)

        if (senderkey != null) {
            senderRef.child(authtoken).child(CommonUtils.getUserId().toString()).child(senderkey).setValue(Senddata)
        }
        if (receiverkey != null) {
            receiverRef.child(CommonUtils.getUserId().toString()).child(authtoken).child(receiverkey).setValue(Receivedata)
        }
        binding.edt.setText("")

        checkNodes()
    }

    private fun checkNodes() {
        Log.d("onDataChangeSender", "checkNodes: "+ authtoken)
        senderRef.child(authtoken).child(CommonUtils.getUserId().toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                senderList.clear()
                for (postSnapshot in snapshot.children) {
                    val sender: ChatDetail? = postSnapshot.getValue(ChatDetail::class.java)
                    if (sender != null) {
                        senderList.add(sender)
                    }
                    else{
                        CommonUtils.xToast(requireContext(),"null h")
                    }
                    chattingAdapter = ChattingChatAdapter(senderList,object :
                        ChattingChatAdapter.CallbackClick {
                        override fun deleteItem(position: Int, key: String, auth_token: String) {
                            hitdeleteNode(key,auth_token)
                        }
                    })
                    binding.chatRecycler.adapter = chattingAdapter
                }
                Log.d("onDataChangeSender", "onDataChange: " + senderList.size)

            }

            override fun onCancelled(error: DatabaseError) {
                CommonUtils.xToast(requireContext(),error.message)
            }

        })
    }

    private fun hitdeleteNode(key: String, auth_token: String) {

        Log.d("hitdeleteNode", "hitdeleteNode: "+ CommonUtils.getUserId().toString() + "," + key )
        senderRef.child(authtoken).child(CommonUtils.getUserId().toString()).child(key).removeValue()
        databaseRef.child(CommonUtils.getUserId().toString()).child(authtoken).child("message").setValue("")
        chattingAdapter.notifyDataSetChanged()
    }

    private fun getData() {
//        arrayUserList = ((arguments?.getSerializable("myList") as? List<Detail>)!!)               **** DETAIL IN LIST THROUGH BUNDLE ****
//        position = arguments?.getInt("position")!!

        authtoken = arguments?.getString("authtoken").toString()
        img = arguments?.getString("img").toString()
        name = arguments?.getString("name").toString()

        binding.name.setText(name)
        Glide.with(requireContext()).load(img).placeholder(R.drawable.user_placeholder).into(binding.profileImg)
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<ConstraintLayout>(R.id.header)!!.visibility = View.VISIBLE
    }
}