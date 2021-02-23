package com.vladislav.workout.timer

import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vladislav.workout.R
import java.lang.ref.Reference
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*
import java.util.logging.SimpleFormatter

class TimerViewModel : ViewModel() {

    private val timer = object : CountDownTimer(90_999, 10) {
        override fun onFinish() {
            time.value = 0
            isTimerInProcess.value = false
        }

        override fun onTick(millisUntilFinished: Long) {
            time.value = millisUntilFinished.toInt()
        }
    }

    var isTimerInProcess = MutableLiveData<Boolean>(false)

    var time = MutableLiveData<Int>(0)

    private val totalSets = 12
    var set = MutableLiveData<Int>(0)

    var timeString: LiveData<String> = Transformations.map(time) {
        val dateTime = Date(time.value!!.toLong())
        val dateTimeFormatter = SimpleDateFormat("mm:ss.SS", Locale.ENGLISH)
        dateTimeFormatter.format(dateTime)
    }

    var stateString = MutableLiveData<String>("")

    var setWithTotalString: LiveData<String> = Transformations.map(set) {
        "${it}/${totalSets}"
    }

    var backgroundColor = MutableLiveData<Int>(0)
    var foregroundColor = MutableLiveData<Int>(0)

    fun startTimer() {
        if (isTimerInProcess.value == false && set.value!! < totalSets) {
            set.value = set.value!! + 1
            isTimerInProcess.value = true
            timer.cancel()
            timer.start()
        }
    }
}