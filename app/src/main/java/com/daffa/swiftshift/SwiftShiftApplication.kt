package com.daffa.swiftshift

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SwiftShiftApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
    }
}