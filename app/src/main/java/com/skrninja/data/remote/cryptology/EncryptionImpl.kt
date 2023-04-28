package com.skrninja.data.remote.cryptology


class EncryptionImpl : CryptoStrategy {

    override fun encrypt(body: String): String {
        return Crypto.encrypt(body)
    }

    override fun decrypt(data: String): String? {
        TODO("Not yet implemented")
    }

}