package com.example.randomscanapp

import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.randomscanapp.data.ItemInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val PREFS_NAME = "PREFS_NAME"
const val ITEM_INFOS_KEY = "ITEM_INFOS"

fun writeData(activity: AppCompatActivity, dataToWrite: ArrayList<ItemInfo>) {
    Thread {
        val json: String = dataToWrite.asJson()
        Log.d("json", json)
        val editor = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        editor.putString(ITEM_INFOS_KEY, json)
        editor.apply()
    }.start()
}

fun ArrayList<ItemInfo>.asJson(): String {
    return Gson().toJson(this)
}

fun fromJson(json: String): ArrayList<ItemInfo> {
    val myType = object : TypeToken<List<ItemInfo>>() {}.type
    return ArrayList(Gson().fromJson<List<ItemInfo>>(json, myType))
}