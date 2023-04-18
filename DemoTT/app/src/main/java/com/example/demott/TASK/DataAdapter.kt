package com.example.demott.TASK

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.demott.Modal.DetailData
import com.example.demott.Modal.TimelineInfo
import com.example.demott.R
import com.example.demott.databinding.ListAppDetailBinding
import com.example.demott.databinding.ListChatBinding
import com.example.demott.view.adapter.AdapterChats

class DataAdapter(var listItemInfo : List<TimelineInfo>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    class ViewHolder( val binding : ListAppDetailBinding)  : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListAppDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listItemInfo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.circular.setText(listItemInfo.get(position).status_short)
        holder.binding.textone.setText(listItemInfo.get(position).status)
        holder.binding.time.setText(listItemInfo.get(position).estimated)
        holder.binding.texttwo.setText("Actual Date : " + listItemInfo.get(position).actual)
       if (listItemInfo.get(position).lines.isEmpty()){
           holder.binding.imgOne.visibility = View.GONE
           holder.binding.imgTwo.visibility = View.GONE
       }
        if (listItemInfo.get(position).has_color==true){
            holder.binding.circular.setBackgroundResource(R.drawable.circle);
        }
        else{
            holder.binding.circular.setBackgroundResource(R.drawable.grey_circle);
        }
        if(listItemInfo.get(position).status_code.equals("completed")){
            holder.binding.imgOne.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.binding.imgTwo.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else if (listItemInfo.get(position).status_code.equals("completedPending")){
            holder.binding.imgTwo.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.binding.imgOne.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        else{
            holder.binding.imgOne.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);
            holder.binding.imgTwo.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN);

        }
    }
}