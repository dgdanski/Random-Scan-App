package com.example.randomscanapp.data.enums

import com.example.randomscanapp.R

enum class Color constructor(val value: String, val colorResId: Int) {
    YELLOW("yellow", R.color.yellow),
    GREEN("green", R.color.green),
    BLUE("blue", R.color.blue),
    RED("red", R.color.red),
    BLACK("black", R.color.black),
    WHITE("white", R.color.white);
}