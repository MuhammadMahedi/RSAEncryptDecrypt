package com.example.rsaencryptdecrypt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rsaencryptdecrypt.databinding.ActivityMainBinding
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var publicKey: PublicKey? = null
    private var privateKey: PrivateKey? = null
    private var encryptedText: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Generate KeyPair on app start
        generateKeyPair()

        binding.btnEncrypt.setOnClickListener {
            val textToEncrypt = binding.editText.text.toString()
            try {
                encryptedText = encrypt(textToEncrypt)
                binding.textViewEncrypted.text = encryptedText?.let { String(it) } ?: "Encryption Failed"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.btnDecrypt.setOnClickListener {
            try {
                val decryptedText = encryptedText?.let { decrypt(it) }
                binding.textViewDecrypted.text = decryptedText ?: "Decryption Failed"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun generateKeyPair() {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            val keyPair: KeyPair = keyPairGenerator.generateKeyPair()
            publicKey = keyPair.public
            privateKey = keyPair.private
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun encrypt(textToEncrypt: String): ByteArray {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(textToEncrypt.toByteArray())
    }

    private fun decrypt(textToDecrypt: ByteArray): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return String(cipher.doFinal(textToDecrypt))
    }
}