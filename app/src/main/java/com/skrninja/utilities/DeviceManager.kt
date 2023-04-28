package com.skrninja.utilities

import android.content.Context
import android.os.Build
import android.provider.Settings.*
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun getDeviceId(): String {

        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)

    }



    fun geDeviceUniqueId(){
       /* var board=Build.BOARD.length
        var brand=Build.BRAND.length
        var deviceL=Build.DEVICE.length
        var display=Build.DISPLAY.length
        var host=Build.HOST.length
        var id=Build.ID.length
        var manufacture=Build.MANUFACTURER.length
        var model=Build.MODEL.length
        var product=Build.PRODUCT.length
        var tag=Build.TAGS.length
        var type=Build.TYPE.length
        var user=Build.USER.length*/
        val pseudoId = Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10+ Build.TAGS.length % 10 + Build.TYPE.length + Build.USER.length % 10
        Timber.i("Length:-$pseudoId")
        val sid = Build.BOARD + Build.BRAND + Build.DEVICE + Build.DISPLAY + Build.HOST + Build.ID + Build.MANUFACTURER + Build.MODEL + Build.PRODUCT+ Build.TAGS + Build.TYPE + Build.USER
        Timber.i("SID:-$sid")
        val board=Build.BOARD
        val brand=Build.BRAND
        val deviceL=Build.DEVICE
        val display=Build.DISPLAY
        val host=Build.HOST
        val id=Build.ID
        val manufacture=Build.MANUFACTURER
        val model=Build.MODEL
        val product=Build.PRODUCT
        val tag=Build.TAGS
        val type=Build.TYPE
        val user=Build.USER

        Timber.i("Modules:-${Build.BOARD.length%10}")
        Timber.i("Board:-$board")
        Timber.i("Brand Name:-$brand")
        Timber.i("Device:-$deviceL")
        Timber.i("Display:-$display")
        Timber.i("Host:-$host")
        Timber.i("Id:-$id")
        Timber.i("Manufacture:-$manufacture")
        Timber.i("Model:-$model")
        Timber.i("Product:-$product")
        Timber.i("Tags:-$tag")
        Timber.i("Type:-$type")
        Timber.i("User:-$user")

    }
}