package com.example.universitysystem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universitysystem.data.models.Dish
import com.example.universitysystem.databinding.OneDishLayoutBinding

class DishesAdapter(private val list:ArrayList<Dish>): RecyclerView.Adapter<DishesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:OneDishLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(dish: Dish){
            binding.tvDishName.text = dish.name
            binding.tvCalorie.text = "Calorie ${dish.calorie}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OneDishLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}