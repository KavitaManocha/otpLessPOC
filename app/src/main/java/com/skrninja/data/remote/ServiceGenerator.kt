package com.skrninja.data.remote

import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import com.skrninja.data.local.SessionManager
import com.skrninja.data.remote.cryptology.DecryptionImpl
import com.skrninja.data.remote.cryptology.EncryptionImpl
import com.skrninja.data.remote.cryptology.intercepter.DecryptionInterceptor
import com.skrninja.data.remote.cryptology.intercepter.EncryptionInterceptor
import com.skrninja.data.remote.intercepter.HeaderInterceptor
import com.skrninja.data.remote.intercepter.NetworkErrorInterceptor
import com.skrninja.mvvm.BuildConfig.API_URL
import com.skrninja.utilities.DeviceManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sunil
 */
private const val timeoutRead = 30   //In seconds
private const val timeoutConnect = 30   //In seconds
/* const val APP_ID = "ApplicationId" */

@Singleton
class ServiceGenerator @Inject constructor(
    sessionManager: SessionManager,
    deviceManager: DeviceManager
) {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private lateinit var retrofit: Retrofit




    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        try {


            //Encryption Interceptor
            val encryptionInterceptor = EncryptionInterceptor(EncryptionImpl())
            //Decryption Interceptor
            val decryptionInterceptor = DecryptionInterceptor(DecryptionImpl())
            //okHttpBuilder.addInterceptor(encryptionInterceptor)
            // okHttpBuilder.addInterceptor(decryptionInterceptor)

            okHttpBuilder.addInterceptor(NetworkErrorInterceptor(sessionManager))
            val headerInterceptor = HeaderInterceptor(sessionManager, deviceManager)
            okHttpBuilder.addInterceptor(headerInterceptor)
            okHttpBuilder.addInterceptor(logger)
            okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
            okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val client = okHttpBuilder.build()
            retrofit = Retrofit.Builder()
                .baseUrl(API_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }


}
