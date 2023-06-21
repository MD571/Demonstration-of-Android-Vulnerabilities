package com.example.androidvulnerabilities

import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESCipher(private val key: ByteArray) {
    private val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")

    init {
        require(key.size == 16 || key.size == 24 || key.size == 32) { "Key size must be 16, 24, or 32 bytes" }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(plaintext: String): String {
        val iv = ByteArray(16)
        val random = SecureRandom()
        random.nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), IvParameterSpec(iv))
        val ciphertext = cipher.doFinal(plaintext.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(iv + ciphertext)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(ciphertext: String): String {
        val decodedCiphertext = Base64.getDecoder().decode(ciphertext)
        val iv = decodedCiphertext.sliceArray(0..15)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        val plaintext = cipher.doFinal(decodedCiphertext.sliceArray(16 until decodedCiphertext.size))
        return String(plaintext, StandardCharsets.UTF_8)
    }

    private fun getKey(): Key {
        return SecretKeySpec(key, "AES")
    }
}
