package com.example.androidvulnerabilities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.text.Html
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.androidvulnerabilities.databinding.ActivityMemoryCorruptionBinding
import java.io.File
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec

class memory_corruption : AppCompatActivity() {
    private lateinit var binding: ActivityMemoryCorruptionBinding
    @SuppressLint("NewApi", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMemoryCorruptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=getIntent()
        val SL=intent.getStringExtra("SL").toString()
        if(SL=="high")
            binding.securityLevel.text="Security Level = High"
        else
            binding.securityLevel.text="Security Level = Low"
        binding.sear.setOnClickListener {
            val editText = binding.search1
            val userInput = editText.text.toString()
            if(SL=="low") {


                val fileName = "user_data.txt"
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(path, fileName)
                if (file.exists()) {
                    file.writeText(userInput)
                    val data = file.readText()
                    binding.viewtext.text = data
                }
                else{
                    file.createNewFile()
                    file.writeText(userInput)
                }
                editText.text.clear()

                // Decrypt the user data and display it in the text view
                //val fileName = "user_data"
                //val file = File(applicationContext.filesDir, fileName)
                if (file.exists()) {
                    val data = file.readText()

                    binding.viewtext.text = "Password: "+data

                }

            }
            else{
                // Encrypt the user input and save it to a file
                // Encrypt the user input and save it to a file

                // Encrypt the user input using Caesar Cipher and save it to a file
                val fileName = "user_dataH.txt"
                val key = "mysecretkey12345".toByteArray()
                val cipher = AESCipher(key)

                val encryptedData = cipher.encrypt(userInput)
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(path, fileName)
                if (file.exists()) {
                    file.writeText(encryptedData)
                    val data = file.readText()
                    binding.viewtext.text = data
                }
                else{
                    file.createNewFile()
                    file.writeText(encryptedData)
                }

                //binding.viewtext.text=file.toString()
                //Toast.makeText(this,file.toString(),Toast.LENGTH_LONG).show()

                // Clear the input field
                editText.text.clear()

                // Decrypt the user data and display it in the text view
                //val fileName = "user_data"
                //val file = File(applicationContext.filesDir, fileName)
                if (file.exists()) {
                    val encryptedData = file.readText()
                    val decryptedData = cipher.decrypt(encryptedData)

                    binding.viewtext.text = "Password: "+decryptedData+"\nHash Password: "+encryptedData

                }

                /*if (file.exists()) {
                    val encryptedData = file.readBytes()

                    val keyStore = KeyStore.getInstance("AndroidKeyStore")
                    keyStore.load(null)
                    val secretKeyEntry = keyStore.getEntry(keyName, null) as KeyStore.SecretKeyEntry
                    val secretKey = secretKeyEntry.secretKey

                    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(encryptedData.copyOfRange(0, 16)))
                    val decryptedData = cipher.doFinal(encryptedData.copyOfRange(16, encryptedData.size))

                    binding.viewtext.text = decryptedData.toString()
                }*/

            }


        }
    }
    private fun encrypt(input: String, shift: Int): String {
        val result = StringBuilder()
        for (c in input) {
            when {
                c.isLowerCase() -> {
                    val shiftedChar = (((c.toInt() - 97) + shift) % 26 + 97).toChar()
                    result.append(shiftedChar)
                }
                c.isUpperCase() -> {
                    val shiftedChar = (((c.toInt() - 65) + shift) % 26 + 65).toChar()
                    result.append(shiftedChar)
                }
                else -> {
                    result.append(c)
                }
            }
        }
        return result.toString()
    }

    // Caesar Cipher decryption function
    private fun decrypt(text: String, key: Int): String {
        return encrypt(text, 26 - key)
    }
}