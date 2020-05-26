package com.example.tenderosapp.util

import android.util.Base64
import com.example.tenderosapp.BuildConfig
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.Key
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Encrypter {
    companion object {
        fun encodeData(activationCode : String, requestUniqueID : String, methodName : String, requestIP : String) : String {
            val data = "{\"MethodName\":\"$methodName\",\"ActivationCode\":\"$activationCode\",\"RequestUniqueID\":\"$requestUniqueID\",\"RequestIP\":\"$requestIP\"}"
            val datos_post = Base64.encodeToString(encrypt(
                aesKey = BuildConfig.ENCRYPTION_KEY,
                hmacKey=  BuildConfig.SIGN_KEY,
                data =  data.toByteArray()
                ), Base64.DEFAULT
            )
            return datos_post //Cadena Cifrada Lista Para Enviar
        }

        fun encodeQRData(issuerUsername : String, issuerStore : String, dateIssued : String) : String {
            val data = "{\"Store\":\"$issuerStore\",\"Username\":\"$issuerUsername\",\"Date\":\"$dateIssued\"}"
            val datos_post = Base64.encodeToString(encrypt(
                aesKey = BuildConfig.ENCRYPTION_KEY,
                hmacKey=  BuildConfig.SIGN_KEY,
                data =  data.toByteArray()
            ), Base64.DEFAULT
            )
            return datos_post //Cadena Cifrada Lista Para Enviar
        }
        fun encodePromoData(issuerUsername : String, issuerStore : String, promoID : String, deviceID : String) : String {
            val data = "{\"Store\":\"$issuerStore\",\"Username\":\"$issuerUsername\",\"Date\":\"$promoID\",\"Device ID\":\"$deviceID\"}"
            val datos_post = Base64.encodeToString(encrypt(
                aesKey = BuildConfig.ENCRYPTION_KEY,
                hmacKey=  BuildConfig.SIGN_KEY,
                data =  data.toByteArray()
            ), Base64.DEFAULT
            )
            return datos_post //Cadena Cifrada Lista Para Enviar
        }

        fun encodeTransactionData(data : String) : String {
            val datos_post = Base64.encodeToString(encrypt(
                aesKey = BuildConfig.ENCRYPTION_KEY,
                hmacKey=  BuildConfig.SIGN_KEY,
                data =  data.toByteArray()
            ), Base64.DEFAULT
            )
            return datos_post //Cadena Cifrada Lista Para Enviar
        }

        fun decyptData(data_cipher : String) : String {
            // Respuesta en claro a utilizar.
            val encodedKey: ByteArray = Base64.decode(data_cipher.toByteArray(), Base64.DEFAULT)
            val response_clear  = decrypt(aesKey = BuildConfig.ENCRYPTION_KEY , hmacKey = BuildConfig.SIGN_KEY, data =  encodedKey )
            return response_clear!!.toString(Charsets.UTF_8)
        }


        private fun encrypt(aesKey: String?, hmacKey: String?, data: ByteArray?): ByteArray? {
            var encodeData: ByteArray? = null
            return try {
                val AES_CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding")
                //Process Changed https://stackoverflow.com/a/12039611/5319007
                val encodedKey = Base64.decode(aesKey, Base64.DEFAULT)
                val secretKey = SecretKeySpec(encodedKey, 0, encodedKey.size, "AES")
                val randomIv = ByteArray(16)
                nextBytes(randomIv)
                AES_CIPHER.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(randomIv))
                val encryptedMessage = AES_CIPHER.doFinal(data)
                val sign = sign(hmacKey, encryptedMessage) ?: return null
                encodeData = ByteArray(sign.size + encryptedMessage.size + randomIv.size)
                System.arraycopy(randomIv, 0, encodeData, 0, randomIv.size)
                System.arraycopy(sign, 0, encodeData, randomIv.size, sign.size)
                System.arraycopy(
                    encryptedMessage,
                    0,
                    encodeData,
                    randomIv.size + sign.size,
                    encryptedMessage.size
                )
                encodeData
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        private fun generateSecretKey(keyBytes: ByteArray): Key? = SecretKeySpec(keyBytes, 0, keyBytes.size, "AES")

        private fun generateHMAC(keyByte: ByteArray): SecretKeySpec? {
            return  SecretKeySpec(keyByte, "HMAC-SHA-1")
        }

        private fun sign(hmacKey: String?, data: ByteArray?): ByteArray? {
            val encodedKey =
                Base64.decode(hmacKey, Base64.DEFAULT)
            val signingKey = generateHMAC(encodedKey) ?: return null
            val senderMac = Mac.getInstance("HmacSHA1")
            senderMac.init(signingKey)
            return senderMac.doFinal(data)
        }

        private fun decrypt(aesKey: String?, hmacKey: String, data: ByteArray): ByteArray? {
            var decryptByte: ByteArray? = null
            try {
                val iv = Arrays.copyOfRange(data, 0, 16)
                val sentSignature = Arrays.copyOfRange(data, 16, 16 + 20)
                val encryptedMessage = Arrays.copyOfRange(data, 16 + 20, data.size)
                if (!verifySignature(hmacKey, sentSignature, encryptedMessage)) return decryptByte
                val encodedKey = Base64.decode(aesKey, Base64.DEFAULT)
                val secretKey = generateSecretKey(encodedKey) as SecretKey? ?: return decryptByte
                val AES_CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding")
                AES_CIPHER.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
                decryptByte = AES_CIPHER.doFinal(encryptedMessage)
                return decryptByte
            } catch (e: java.lang.Exception) {
                return null
            }
        }

        private fun verifySignature(hmacKey: String, sentSignature: ByteArray, data: ByteArray): Boolean {
            return try {
                val encodedKey = Base64.decode(hmacKey, Base64.DEFAULT)
                val signingKey = generateHMAC(encodedKey) ?: return false
                val senderMac = Mac.getInstance("HmacSHA1")
                senderMac.init(signingKey)
                Arrays.equals(sentSignature, senderMac.doFinal(data))
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        private var secureRandom: SecureRandom? = SecureRandom.getInstance("SHA1PRNG")

        private fun nextBytes(bytes: ByteArray) {
            secureRandom!!.nextBytes(bytes)
        }

        @Throws(IOException::class)
        fun send(Activation_Code: String, datos_post: String): String? {
            val url = URL("https://mw-uat.pagofon.net/securerest")
            val params: MutableMap<String, Any> =
                LinkedHashMap()
            params["ActivationCode"] = Activation_Code
            params["Data"] = datos_post
            val postData = StringBuilder()
            for ((key, value) in params) {
                if (postData.length != 0) postData.append('&')
                postData.append(URLEncoder.encode(key, "UTF-8"))
                postData.append('=')
                postData.append(
                    URLEncoder.encode(
                        value.toString(),
                        "UTF-8"
                    )
                )
            }
            val postDataBytes = postData.toString().toByteArray(charset("UTF-8"))
            println("Peticion POST: $postData")
            val conn =
                url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            )
            conn.setRequestProperty("Content-Length", postDataBytes.size.toString())
            conn.doOutput = true
            conn.outputStream.write(postDataBytes)
            var response = ""
            val `in`: Reader = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"))
            var c = `in`.read()
            while (c != -1) {
                //System.out.print((char) c);
                response = response + c.toChar()
                c = `in`.read()
            }
            return response
        }
    }

}
