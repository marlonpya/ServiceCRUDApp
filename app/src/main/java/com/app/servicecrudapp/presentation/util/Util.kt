package com.app.servicecrudapp.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

@SuppressLint("HardwareIds")
fun Context.getHardwareIds(): String {
    return Settings.Secure.getString(
        this.contentResolver,
        Settings.Secure.ANDROID_ID
    )
}