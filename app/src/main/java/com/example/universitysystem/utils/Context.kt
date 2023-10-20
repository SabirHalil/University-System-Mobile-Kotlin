package com.example.universitysystem.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

fun Context.toast(msg:String?){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.extractDateFromDateTime(dateTimeString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)
    val date = dateTime.toLocalDate()
    return date.toString()
}

fun Context.getCurrentDateTime(): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val currentDateAndTime = Date()
    return dateFormat.format(currentDateAndTime)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.getCurrentSemester(): Pair<Int,String> {
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    val currentDay = currentDate.dayOfMonth

    return when {
        (currentMonth >= Month.MARCH && currentMonth <= Month.JULY) -> {
            Pair(2,"Spring")
        }
        (currentMonth >= Month.AUGUST && currentMonth <= Month.OCTOBER) -> {
          Pair(3, "Summer")
        }
        (currentMonth >= Month.OCTOBER || (currentMonth == Month.SEPTEMBER && currentDay <= 3)) -> {
            Pair(1,"Fall")
        }else -> {
            Pair(0,"")
        }
    }
}