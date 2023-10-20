package com.example.universitysystem.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.R
import com.example.universitysystem.data.models.Notification
import com.example.universitysystem.databinding.OneNotificationLayoutBinding
import com.example.universitysystem.utils.extractDateFromDateTime

class NotificationAdapter(private val list:ArrayList<Notification>):RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: OneNotificationLayoutBinding): RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(notification: Notification){
            binding.tvTitle.text = notification.title
            binding.tvDescription.text = notification.description
            binding.tvDate.text = binding.root.context.extractDateFromDateTime(notification.date)
            binding.ivExpand.setOnClickListener {
                if (notification.expanded){
                    binding.ivExpand.setImageResource(R.drawable.ic_expand_more)
                    binding.tvDescription.maxLines = 2
                }else{
                    binding.ivExpand.setImageResource(R.drawable.ic_expand_less)
                    binding.tvDescription.maxLines = Int.MAX_VALUE
                }
                notification.expanded = !notification.expanded
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OneNotificationLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}