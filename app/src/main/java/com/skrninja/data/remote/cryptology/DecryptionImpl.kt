package com.skrninja.data.remote.cryptology


class DecryptionImpl : CryptoStrategy {
    override fun encrypt(body: String): String? {
        TODO("Not yet implemented")
    }


    override fun decrypt(data: String): String {
        return Crypto.decrypt(data)
    }
}