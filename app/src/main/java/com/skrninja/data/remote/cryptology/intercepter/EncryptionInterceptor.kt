package com.skrninja.data.remote.cryptology.intercepter

import com.skrninja.data.remote.cryptology.Crypto
import com.skrninja.data.remote.cryptology.CryptoStrategy
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber


class EncryptionInterceptor(private val mEncryptionStrategy: CryptoStrategy) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val rawBody = request.body
        var encryptedBody: String? = ""
        // val mediaType: MediaType = parse.parse("text/plain; charset=utf-8")
        try {
            val rawBodyStr = rawBody?.let { Crypto.requestBodyToString(it) }
            encryptedBody = mEncryptionStrategy.encrypt(rawBodyStr!!)
            Timber.i("Raw body=> %s", rawBodyStr)
            Timber.i("Encrypted BODY=> %s", encryptedBody)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = encryptedBody?.toRequestBody("text/plain;charset=utf-8".toMediaType())
        //        request = request.newBuilder().header("User-Agent", "Your-App-Name");
        request = request.newBuilder().header("Content-Type", body?.contentType().toString())
            .header("Content-Length", body?.contentLength().toString())
            .method(request.method, body).build()
        return chain.proceed(request)
    }

    companion object {
        private val TAG = EncryptionInterceptor::class.java.simpleName
    }
}