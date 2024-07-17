package com.dedan.kalenderadat

import android.app.Application
import com.dedan.kalenderadat.data.AppContainer
import com.dedan.kalenderadat.data.DefaultAppContainer

class CalendarApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
