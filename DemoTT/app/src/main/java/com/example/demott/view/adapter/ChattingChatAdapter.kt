package com.example.demott.view.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.demott.Modal.ChatDetail
import com.example.demott.Utils.CommonUtils
import com.example.demott.databinding.ListChatItemBinding

class ChattingChatAdapter(var listChat: List<ChatDetail>, var callbackClick: CallbackClick) :
    RecyclerView.Adapter<ChattingChatAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface CallbackClick {
        fun deleteItem(position: Int, key: String, auth_token: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListChatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listChat.get(position).type.equals("sender")) {
            holder.binding.sender.setText(listChat.get(position).message)
            holder.binding.sendTime.setText(listChat.get(position).timestamp)
            holder.binding.reciverTime.visibility = View.GONE
            holder.binding.reciver.visibility = View.GONE
        } else {
            holder.binding.reciver.setText(listChat.get(position).message)
            holder.binding.sender.visibility = View.GONE
            holder.binding.sendTime.visibility = View.GONE
            holder.binding.reciverTime.setText(listChat.get(position).timestamp)
        }
        holder.binding.sender.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                CommonUtils.xToast(holder.itemView.context, "clicked")
                var popupMenu = PopupMenu(holder.binding.sender.context, holder.binding.sender)
                popupMenu.menu.add(Menu.NONE, 0, 0, "Delete")
                popupMenu.menu.add(Menu.NONE, 2, 2, "Copy")
                popupMenu.show()

                popupMenu.setOnMenuItemClickListener { item ->
                    val id = item.itemId
                    if (id == 0) {
                            callbackClick.deleteItem(position, listChat.get(position).key,listChat.get(position).auth_token)
                    }
                    if (id == 2) {
                        val clipboard =
                            holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("text", listChat.get(position).message)
                        assert(clipboard != null)
                        clipboard.setPrimaryClip(clip)
                    }

                    false
                }
                return true
            }
        })

        holder.binding.reciver.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                CommonUtils.xToast(holder.itemView.context, "clicked")
                var popupMenu = PopupMenu(holder.binding.reciver.context, holder.binding.sender)
                popupMenu.menu.add(Menu.NONE, 0, 0, "Copy")
                popupMenu.show()

                popupMenu.setOnMenuItemClickListener { item ->
                    val id = item.itemId
                    if (id == 0) {
                        val clipboard =
                            holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("text", listChat.get(position).message)
                        assert(clipboard != null)
                        clipboard.setPrimaryClip(clip)
                    }

                    false
                }
                return true
            }
        })


    }
}