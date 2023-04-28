package com.skrninja.data.local

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.skrninja.data.remote.intercepter.APP_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        const val PREFS_NAME = "yourAppName"
        const val LOGIN_ = "login_"
        const val FCM_TOKEN = "fcm_token"
        const val AUTH_TOKEN = "auth_token"
        const val IS_ALLOWED = "is_allowed"
        const val LAUNCH_FIRST_TIME = "launch"
    }


    fun logOut() {
        val editor: SharedPreferences.Editor
        val settings: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
        editor.clear()
        editor.apply()


    }


    fun storeIsFirstTime(isFirstTime: Boolean) {
        val editor: SharedPreferences.Editor
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            LAUNCH_FIRST_TIME,
            Context.MODE_PRIVATE
        )
        editor = sharedPref.edit()
        editor.putBoolean("isFirst", isFirstTime)
        editor.apply()
    }

    fun getFirstTime(): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            LAUNCH_FIRST_TIME,
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean("isFirst", false)
    }

    // Store Firebase Token
    fun storeFcmToken(token: String) {
        val editor: SharedPreferences.Editor
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = sharedPref.edit()
        editor.putString(FCM_TOKEN, token)
        editor.apply()
    }

    // get Firebase Token
    fun getFcmToken(): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        return sharedPref.getString(FCM_TOKEN, "")
    }

    // store appId
    fun storeAppId(appId: String?) {
        val editor: SharedPreferences.Editor
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        editor.putString(APP_ID, appId)
        editor.apply()
    }

    // get appId
    fun getAppId(): String? {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(APP_ID, "")
    }

    // Store Authorization Token
    fun storeAuthToken(token: String?) {
        val editor: SharedPreferences.Editor
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = sharedPref.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    // get Authorization Token
    fun getAuthToken(): String? {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(AUTH_TOKEN, "")
    }


    // for store notification permission allowed or not
    fun storeNotificationPermission(isAllow: Boolean) {
        Timber.i("--setPermission$isAllow")
        val editor: SharedPreferences.Editor
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            IS_ALLOWED,
            Context.MODE_PRIVATE
        )
        editor = sharedPref.edit()
        editor.putBoolean("is_allowed", isAllow)
        editor.apply()
    }

    // for get notification permission allowed or not
    fun getNotificationPermission(): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(
            IS_ALLOWED,
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean("is_allowed", false)
    }
}

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))

}