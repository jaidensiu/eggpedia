package com.jaidensiu.eggpedia.ui.home

expect fun getTimeOfDay(): String
expect fun registerTimeChangeListener(onTimeChange: () -> Unit)
expect fun unregisterTimeChangeListener()
