package com.example.noteappcompose.utils

import java.text.SimpleDateFormat
import java.util.Date

class Tools {
    companion object{
        fun convertLongToTime(time:Long):String{
            val date= Date(time)
            val format=SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
        }
    }
}