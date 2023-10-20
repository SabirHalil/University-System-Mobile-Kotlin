package com.example.universitysystem.utils

import com.example.universitysystem.data.models.Location
import com.google.android.gms.maps.model.LatLng

object StudentConstant{
    var TOKEN:String? = null
    var ID =0
}
object MapList{
    fun getMapList():ArrayList<Location>{
        val list = ArrayList<Location>()
        list.add(Location("Engineering Faculty",LatLng(38.70811187187356,35.52423966269934)))
        list.add(Location("Medicine Faculty",LatLng(38.7041408580867, 35.52375234087618)))
        list.add(Location("Faculty of Economics and Administrative Sciences",LatLng(38.70621230419137,35.52224367276617)))
        list.add(Location("Student Affairs Department",LatLng(38.70715072734834,35.52834422425011)))
        list.add(Location("Tourism Faculty",LatLng(38.70886027043702,35.53442784242814)))
        return list
    }
}