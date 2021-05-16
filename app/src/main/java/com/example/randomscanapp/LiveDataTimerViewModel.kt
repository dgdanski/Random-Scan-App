package com.example.randomscanapp

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class LiveDataTimerViewModel : ViewModel() {

    private val ONE_SECOND = 1000
    private val FIVE_SECOND = 5000

    private val mElapsedTime = MutableLiveData<Long>()

    private var mInitialTime: Long = 0
    private var timer: Timer? = null

    init {
        mInitialTime = SystemClock.elapsedRealtime()
        timer = Timer()

        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), FIVE_SECOND.toLong())
    }

    fun getElapsedTime(): LiveData<Long>? {
        return mElapsedTime
    }

    override fun onCleared() {
        super.onCleared()
        timer!!.cancel()
    }
}