package com.example.demott.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demott.Modal.ChatDetail
import com.example.demott.R
import com.example.demott.databinding.ListChatBinding

class AdapterChats(var listOfUserChat : List<ChatDetail> ,var callbackClick : ClickCallback) : RecyclerView.Adapter<AdapterChats.MyViewHolder>() {
    interface  ClickCallback{
        fun click(position : Int)
    }
    class MyViewHolder(val binding : ListChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         return MyViewHolder(ListChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return listOfUserChat.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (listOfUserChat.get(position).name.equals("")){
            holder.binding.name.setText("anonymous")
        }else {
            holder.binding.name.setText(listOfUserChat.get(position).name)
        }
        Glide.with(holder.itemView.context).load(listOfUserChat.get(position).image).placeholder(R.drawable.user_placeholder).into(holder.binding.img)
        holder.binding.message.setText(listOfUserChat.get(position).message)
        holder.binding.authKey.setText(listOfUserChat.get(position).auth_token)

        holder.itemView.setOnClickListener {
            callbackClick.click(position)
        }
    }
}