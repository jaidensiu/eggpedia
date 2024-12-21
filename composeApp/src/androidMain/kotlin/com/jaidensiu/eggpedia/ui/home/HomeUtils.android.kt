package com.jaidensiu.eggpedia.ui.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import org.koin.java.KoinJavaComponent.inject
import java.util.Calendar

actual fun getTimeOfDay(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "morning"
        in 12..17 -> "afternoon"
        else -> "evening"
    }
}

private var timeChangeReceiver: BroadcastReceiver? = null

actual fun registerTimeChangeListener(onTimeChange: () -> Unit) {
    val context: Context by inject(Context::class.java)
    timeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onTimeChange()
        }
    }.also {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
        }
        context.registerReceiver(it, filter)
    }
}

actual fun unregisterTimeChangeListener() {
    val context: Context by inject(Context::class.java)
    timeChangeReceiver?.let {
        context.unregisterReceiver(it)
        timeChangeReceiver = null
    }
}
