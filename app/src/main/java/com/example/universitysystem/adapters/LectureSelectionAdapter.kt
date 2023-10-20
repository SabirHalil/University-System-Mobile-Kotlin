package com.example.universitysystem.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.R
import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.databinding.OneLectureLayoutBinding

class LectureSelectionAdapter(private val list :ArrayList<Lecture>, val onAddOrDeleteClicked: OnAddOrDeleteClicked,val isAdd:Boolean): RecyclerView.Adapter<LectureSelectionAdapter.ViewHolder>() {
    interface OnAddOrDeleteClicked{
        fun onAddOrDeleteClicked(id:Int, isAdd:Boolean)
    }
    inner class ViewHolder(private val binding: OneLectureLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(lecture: Lecture){
            binding.tvCode.text = lecture.lectureCode
            binding.tvName.text = lecture.lectureName
            binding.tvKredi.text = lecture.lectureCredit.toString()
            if (isAdd){
                val greenColor = ContextCompat.getColor(binding.root.context, R.color.green)

                binding.view1.setBackgroundColor(greenColor)
                binding.ivAdd.setImageResource(R.drawable.ic_add)
            }
            binding.ivAdd.setOnClickListener {
                onAddOrDeleteClicked.onAddOrDeleteClicked(adapterPosition,isAdd)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(OneLectureLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}