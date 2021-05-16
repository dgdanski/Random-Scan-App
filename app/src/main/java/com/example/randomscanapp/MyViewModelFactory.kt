package com.example.randomscanapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveDataTimerViewModel::class.java)) {
            return LiveDataTimerViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}