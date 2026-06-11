package com.app.servicecrudapp.presentation.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest
import java.util.UUID

data class DeviceIdentifiers(
    val androidId: String,
    val deviceFingerprint: String,
    val stableDeviceId: String
)

fun Context.getHardwareIds(): String {
    val devices = getHardwareId()
    return "${devices.stableDeviceId}*${devices.deviceFingerprint}*${devices.androidId}"
}

@SuppressLint("HardwareIds")
private fun Context.getHardwareId(): DeviceIdentifiers {
    val androidId = getAndroidId()
    val fingerprint = getDeviceFingerprint()
    val stableId = buildStableDeviceId(androidId, fingerprint)

    return DeviceIdentifiers(
        androidId = androidId,
        deviceFingerprint = fingerprint,
        stableDeviceId = stableId
    )
}

@SuppressLint("HardwareIds")
private fun Context.getAndroidId(): String {
    return try {
        val id = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        if (id.isNullOrBlank() || id == "9774d56d682e549c") {
            UUID.randomUUID().toString()
        } else {
            id
        }
    } catch (e: Exception) {
        UUID.randomUUID().toString()
    }
}

private fun getDeviceFingerprint(): String {
    return buildString {
        append(Build.MANUFACTURER)
        append("|")
        append(Build.MODEL)
        append("|")
        append(Build.BRAND)
        append("|")
        append(Build.DEVICE)
        append("|")
        append(Build.PRODUCT)
        append("|")
        append(Build.HARDWARE)
        append("|")
        append(Build.BOARD)
    }
}


private fun buildStableDeviceId(androidId: String, fingerprint: String): String {
    val raw = "$androidId|$fingerprint"
    return raw.toSHA256()
}

private fun String.toSHA256(): String {
    return try {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(this.toByteArray(Charsets.UTF_8))
        hashBytes.joinToString("") { "%02x".format(it) }
    } catch (e: Exception) {
        this.hashCode().toString()
    }
}