package com.example.universitysystem.data.models

data class Meal(
    val date:String,
    val type:Int,
    val dishes:ArrayList<Dish>
){
    fun getTotalCalorie():Int{
        return dishes.sumOf { it.calorie }
    }
}