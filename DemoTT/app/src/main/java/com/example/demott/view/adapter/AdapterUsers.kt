package com.example.demott.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.demott.Modal.Detail
import com.example.demott.R
import com.example.demott.databinding.ListItemCountBinding

class AdapterUsers(var  arrayUserList : List<Detail>,var clickcallback : ClickCallback) : RecyclerView.Adapter<AdapterUsers.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemCountBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUsers.MyViewHolder {
      return MyViewHolder(ListItemCountBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AdapterUsers.MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(arrayUserList.get(position).image).placeholder(R.drawable.user_placeholder).into(holder.binding.img)
        if (arrayUserList.get(position).name.equals("")){
            holder.binding.name.setText("anonymous")
        }
        else{
            holder.binding.name.setText(arrayUserList.get(position).name)
        }
        holder.binding.authkey.setText(arrayUserList.get(position).auth_token)

        holder.binding.chat.setOnClickListener{
            clickcallback.clickItem(position)
        }
    }

    override fun getItemCount(): Int {
        return  arrayUserList.size
    }
    interface  ClickCallback{
       fun  clickItem(position : Int)
    }
}