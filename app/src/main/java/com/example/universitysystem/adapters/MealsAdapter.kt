package com.example.universitysystem.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.data.models.Meal
import com.example.universitysystem.databinding.OneMealLayoutBinding
import com.example.universitysystem.utils.extractDateFromDateTime
@RequiresApi(Build.VERSION_CODES.O)
class MealsAdapter(private val list  : ArrayList<Meal>): RecyclerView.Adapter<MealsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:OneMealLayoutBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(meal:Meal){
            binding.tvDate.text = binding.root.context.extractDateFromDateTime(meal.date)
            binding.tvTotalCalorie.text = "Total calorie ${meal.getTotalCalorie()}"
            val adapter = DishesAdapter(meal.dishes)
            binding.rvDishes.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvDishes.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OneMealLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}