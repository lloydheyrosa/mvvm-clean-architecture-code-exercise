package com.lloydheyrosa.contactlist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContactListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}