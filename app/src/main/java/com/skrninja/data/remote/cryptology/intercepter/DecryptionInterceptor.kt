package com.skrninja.data.remote.cryptology.intercepter

import android.text.TextUtils
import com.skrninja.data.remote.cryptology.DecryptionImpl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber

class DecryptionInterceptor(private val mDecryptionStrategy: DecryptionImpl) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            val newResponse = response.newBuilder()
            var contentType = response.header("Content-Type")
            if (TextUtils.isEmpty(contentType)) contentType = "application/json"
            //            InputStream cryptedStream = response.body().byteStream();
            val responseStr = response.body!!.string()
            var decryptedString: String? = null
            try {
                decryptedString = mDecryptionStrategy.decrypt(responseStr)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Timber.i("Response string => %s", responseStr)
            Timber.i("Decrypted BODY=> %s", decryptedString)
            newResponse.body(decryptedString!!.toResponseBody(contentType?.toMediaType()))
            return newResponse.build()
        }
        return response
    }
}