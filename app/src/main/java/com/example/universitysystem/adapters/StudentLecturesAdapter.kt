package com.example.universitysystem.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.R
import com.example.universitysystem.data.models.StudentLectureDTO
import com.example.universitysystem.databinding.OneLecturesLayoutBinding

class StudentLecturesAdapter (private val list :ArrayList<StudentLectureDTO>, private val passed:Boolean, private val deleteListener:OnDeleteLectureListener?=null): RecyclerView.Adapter<StudentLecturesAdapter.ViewHolder>() {
interface OnDeleteLectureListener{
    fun onDeleteLectureListener(id:StudentLectureDTO)
}
    inner class ViewHolder(private val binding: OneLecturesLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(lecture: StudentLectureDTO){
            binding.tvCode.text = lecture.lectureCode
            binding.tvName.text = lecture.lectureName
            binding.tvVize.text = lecture.midterm.toString()
            binding.tvFinal.text = lecture.final.toString()
            if (passed){
                val blueColor = ContextCompat.getColor(binding.root.context, R.color.blue)
                binding.view1.setBackgroundColor(blueColor)
            }
            val total = ((lecture.midterm * 0.4 + lecture.final * 0.6))
            if (total >0.0)
                binding .tvTotal.text =total.toString()

            binding.root.setOnLongClickListener {
                    deleteListener?.onDeleteLectureListener(lecture)

                true
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(OneLecturesLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}