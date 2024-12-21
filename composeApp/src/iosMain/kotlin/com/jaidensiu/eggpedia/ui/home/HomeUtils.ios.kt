package com.jaidensiu.eggpedia.ui.home

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarDayChangedNotification
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSDate
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject

actual fun getTimeOfDay(): String {
    val calendar = NSCalendar.currentCalendar
    val hour = calendar.component(NSCalendarUnitHour, fromDate = NSDate())
    return when (hour) {
        in 0..11 -> "morning"
        in 12..17 -> "afternoon"
        else -> "evening"
    }
}

private var timeChangeObserver: NSObject? = null

@OptIn(ExperimentalForeignApi::class)
actual fun registerTimeChangeListener(onTimeChange: () -> Unit) {
    timeChangeObserver = NSObject().apply {
        NSNotificationCenter.defaultCenter.addObserver(
            observer = this,
            selector = NSSelectorFromString(aSelectorName = "timeChanged"),
            name = NSCalendarDayChangedNotification,
            `object` = null
        )
    }
}

actual fun unregisterTimeChangeListener() {
    timeChangeObserver?.let {
        NSNotificationCenter.defaultCenter.removeObserver(it)
        timeChangeObserver = null
    }
}
