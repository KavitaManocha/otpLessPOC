package com.skrninja.data.remote.cryptology

import android.util.Base64
import com.google.gson.Gson
import okhttp3.RequestBody
import okio.Buffer
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

const val TAG_LENGTH = 16

class Crypto {

    companion object {

        fun encrypt(message: String): String {

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
            val iv = cipher.iv.copyOf()
            val result = cipher.doFinal(message.toByteArray())
            val ciphertext = result.copyOfRange(0, result.size - TAG_LENGTH)
            val tag = result.copyOfRange(result.size - TAG_LENGTH, result.size)
            println("========nonce===" + Base64.encodeToString(iv, Base64.DEFAULT))
            println("========tag===" + Base64.encodeToString(tag, Base64.DEFAULT))
            println("========cipher===" + Base64.encodeToString(ciphertext, Base64.DEFAULT))

            val payload = Payload(
                Base64.encodeToString(iv, Base64.DEFAULT),
                Base64.encodeToString(tag, Base64.DEFAULT),
                Base64.encodeToString(ciphertext, Base64.DEFAULT)
            )
            println("========payload===" + Gson().toJson(payload))
            return Gson().toJson(payload)
        }


        fun decrypt(
            payload: String
        ): String {
            val data = Gson().fromJson(payload, Payload::class.java)
            val iv = Base64.decode(data.nonce, Base64.DEFAULT)
            val tag = Base64.decode(data.authTag, Base64.DEFAULT)
            val ciphertext = Base64.decode(data.ciphertext, Base64.DEFAULT)
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(TAG_LENGTH * 8, iv)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
            return String(cipher.doFinal(ciphertext + tag))
        }


        private fun getSecretKey(): SecretKey {
            val sKey = "fdv8FpgzcZ7hZDDNJQ+kRANItyCR552JFeVTaTncFu0="
            val byteKey = Base64.decode(sKey, Base64.DEFAULT)
            return SecretKeySpec(byteKey, 0, byteKey.size, "AES")
        }

        fun requestBodyToString(requestBody: RequestBody): String {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            return buffer.readUtf8()
        }

    }

}