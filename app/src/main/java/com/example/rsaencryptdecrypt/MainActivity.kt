package com.example.rsaencryptdecrypt

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rsaencryptdecrypt.databinding.ActivityMainBinding
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var publicKey: PublicKey? = null
    private var privateKey: PrivateKey? = null
    private var encryptedText: ByteArray? = null
    private var keyPairString :Pair<String, String>? = null
    private  val TAG = "MainActivity"
    val privateString = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmDTg0UdM0j00QKgIXzf83hH2gnA0MIeI2MeGJIRvJ02iNFSZT8XxBtXaIPvgTonQUCKMeM5zx2g/z7pktdLBZ6fkojJp7QvVQxJIjWrmVSawklYI+zxuoOUZeQmldtdoonAojvZxomvpbtWrpG9Gjo5riRegAJygjHkzo9tpXcS8PjGBiQzFo9mQbcGEPEPjAyb8KBqdCfVDEh9pLx7kK3rgyjhqIpMu9g7OgHMRv9GRh6LKcxH950dDBQ+jUgZ/888jWOKuDa0Q7UJAWnK/yERNGnKx4qnQdu/f4G2YS8LVAD0emFQEDpVvKqtNqiSo0hUkr57gSfW2ZbK/E8KM/AgMBAAECggEAUOqgON6mzTYHl5jg7Di15ZfqoBlrQj5AvagQ/4HiyLb8e8OSqdVDd8G1wS7uNKM1bY3H7y+3iyN4IqZifoQea7JYeCoMHYTDhlhD3naXeOgtuqYgkfrav18pq0UxkzjK1n8zcbc0QMVEuuvs7SthPRy8nQbzxx9Nr89UKzIfs/vqdv3oqmmc9t3oqdVlQWJ+FQweCBZfue7aBjrRDczelHieV0Lmw8dXlmLMKHfuhQ2qRCquoPjTS+OwVCTni0ocmnNqMIVKJiTGvGhRH2azAmSGBE7cm44IbxeLd6mFsHiRXhK1/xgkvoTxQ8KRmYe7Fgw45f4puXOUHStaOE9QAQKBgQDZlZC56HGhrkqOeUF7r7PgiY2XTze3l4t+Aqz91pKdB+6wNzUNnKStb3R1fRRqFJHOWHPsBUIAKBMYekC9ISWQIDDxML3V+fMILyi/CzIf1M7IlocCAFk/FFdn9b7qPqUWFroVuEAfRnQ+nYE4s66J90cRLa0XPkdZ05fhXICIXwKBgQDDXngP1gRKuSxZ7qO+toQWvpeT0PYevEVJWpHTJ4ArIo8syttf+oI1Jx1UnqOdlmnm8727NjtH3+zsKJLaQZUzeyhiY26QcX6LTO7DIYq8awzeoANyNcuIK4JclbAg0LfbAQcN393UN/vjpdI3/O9+p2F/aglAcoBbdlEHEWdRIQKBgEAL6YT/qesYnbOgd6kLyBvxAW+njtQI1LOr3/X8Ij385sKSDZjLIiI3K4Wl4sty8JRXrw+rYIkhQrWyzrmdNyxcfWuRmF+S6nnfP9Q0XHXIM3IR9P2e0qZhu3TLfn3/IKMG35GjIaTTHUaNIEtrgJX15sJkuo2b9G65fPyGOQNrAoGAaazasm4oCicZR1feuFTPI+JZ6PmZd0yNCb10rUtrd2mNGkRr3wGIlqmeotvgUmg+xk7HLNGLxTfpVmAn9uiMhLk0QevYrUbGlSC68U9eVMZuwqi41Hkmq3QlYmERUN605EAtCaMywQy7MgrKp8An51EwWpQHhxWoOlDISVNvmCECgYBW0e79htm7ILAiWXuU+Q5G3rMYkYa2DsSzvg594/C8kslB0vv5n7z0eOiHqjFZHaTJlqGKWIfSkHcrLitlGyqHckcxmOlyPGSXwA3BNI29SE8qF/yw6SATCcTpphuFxqGqS9di/x3MACkBCkN/UtAXOTZfm3GUJ2oKdnLvaT76VQ=="

    val encryptedString ="NBtXkwLBOkDDyVCAwf7N7Bi+YEm7Mlr8zvJvXacOuldJ/WmT1go6ukvyXhTZpC8wMKID+o4tdL7SOU+PSyJx0pS8y4bU79axe/z0RUtoTZ8qNhUv/Eze66HkxPxwiXOUkhOyNK60PvVvnw3p6j/tTXDMP0KYEuZprDDRar/dNmjb+IOeQ8Le25XBOLX/723T6nexZAANONkejbIxs3Le4vpfHBKi8KcsSKXUWkWzi7JlBZvaUz4LGb50c22zkMO+IzmUwD7D2RGCS+Fedo/Dj4xA1VzoKrz2uQf056ERdF3tciMs8/eynbCENzvqo5c8Jar14Hs25/rfxo164tjjoA=="

    @RequiresApi(Build.VERSION_CODES.O)
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
//                val decodedDataBytes = Base64.getDecoder().decode(encryptedString)
//                Log.d(TAG, "onCreate: $decodedDataBytes")
//                val decryptedText = decrypt(decodedDataBytes)
//                Log.d(TAG, "onCreate: $decryptedText")
                binding.textViewDecrypted.text = decryptedText ?: "Decryption Failed"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateKeyPair() {
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            val keyPair: KeyPair = keyPairGenerator.generateKeyPair()
            publicKey = keyPair.public
            privateKey = keyPair.private
            keyPairString = convertKeyPairToString(keyPair)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encrypt(textToEncrypt: String): ByteArray {
        val cipher = Cipher.getInstance("RSA")
        val keyPair =convertStringToKeyPair(keyPairString!!.first,keyPairString!!.second)
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.public)
        return cipher.doFinal(textToEncrypt.toByteArray())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decrypt(textToDecrypt: ByteArray): String {
        val cipher = Cipher.getInstance("RSA")
        val keyPair =convertStringToKeyPair(keyPairString!!.first,keyPairString!!.second)
        cipher.init(Cipher.DECRYPT_MODE, keyPair.private)
//        val privateKey = convertStringToPrivateKey(privateString)
//        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return String(cipher.doFinal(textToDecrypt))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertKeyPairToString(keyPair: KeyPair): Pair<String, String> {
        val publicKey = Base64.getEncoder().encodeToString(keyPair.public.encoded)
        val privateKey = Base64.getEncoder().encodeToString(keyPair.private.encoded)

        return publicKey to privateKey
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToKeyPair(publicKeyString: String, privateKeyString: String): KeyPair {

        val keyFactory = KeyFactory.getInstance("RSA")

        val publicKeyBytes = Base64.getDecoder().decode(publicKeyString)
        val privateKeyBytes = Base64.getDecoder().decode(privateKeyString)

        val publicKeySpec = X509EncodedKeySpec(publicKeyBytes)
        val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)

        val publicKey = keyFactory.generatePublic(publicKeySpec)
        val privateKey = keyFactory.generatePrivate(privateKeySpec)

        return KeyPair(publicKey, privateKey)

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToPrivateKey(privateKeyString: String) : PrivateKey {
        val keyFactory = KeyFactory.getInstance("RSA")

        val privateKeyBytes = Base64.getDecoder().decode(privateKeyString)
        val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
        val privateKey = keyFactory.generatePrivate(privateKeySpec)

        return privateKey
    }
}