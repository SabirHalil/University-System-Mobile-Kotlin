package com.example.universitysystem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.data.models.Faq
import com.example.universitysystem.databinding.OneFaqLayoutBinding
import com.example.universitysystem.network.FaqApi

class FaqAdapter(val list: ArrayList<Faq>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: OneFaqLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: Faq) {
            binding.tvQuestion.text = faq.question
            binding.tvAnswer.text = faq.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneFaqLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}