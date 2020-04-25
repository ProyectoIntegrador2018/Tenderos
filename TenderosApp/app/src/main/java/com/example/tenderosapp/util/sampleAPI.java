package com.example.tenderosapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class sampleAPI {

	/*
	*
	* PLEASE DO NOT USE THIS CLASS,THIS IS ONLY A CLASS EXAM?LE
	* The real class used for encrypt/decript is:  "util/Encrypter"
	* And the the http connection is: All the "network" package
	*
	* */

	public static void main(String[] args) {
		String Activation_Code = "1169325819";
		String Encryption_key = "lz3M0IH4swwYCR/vcOqXPg==";
		String Sign_Key = "lz3M0IH4swwYCR/vcOqXPg==";
		String RequestUniqueID="3456673745482112";
		String data = "{\"MethodName\":\"GetBalance\",\"ActivationCode\":\""+Activation_Code+"\",\"RequestUniqueID\":\""+RequestUniqueID+"\",\"RequestIP\":\"189.213.47.65\"}";

        System.out.println("Cadena: "+data);
        sampleAPI class1 = new sampleAPI();
  		String datos_post= Base64.encodeToString(class1.encrypt(Encryption_key, Sign_Key, data.getBytes()),Base64.DEFAULT) ; //This line was fixed byhttps://stackoverflow.com/a/28426808/5319007
		System.out.println("Cadena cifrada: "+datos_post);
		String data_response="";
		JsonParser parser = new JsonParser();
		
		try {
			System.out.println("Inicia peticion API");
			data_response = send (Activation_Code, datos_post);   //URL CONNECT
			System.out.println("Termina consumir API");
			System.out.println("Response API: "+data_response);
			// PARSE JSON
			JsonElement jsonTree = parser.parse(data_response);
			JsonObject jsonObject = jsonTree.getAsJsonObject();    
			// GET DATA of JSON
			String data_cipher = jsonObject.get("Data").getAsString(); 
			// Respuesta en claro a utilizar.
			byte[] encodedKey     = Base64.decode(data_cipher.getBytes(), Base64.DEFAULT);
			String response_clear = new String(class1.decrypt(Encryption_key, Sign_Key, encodedKey));
			System.out.println("Respuesta API descifrada: "+response_clear);

		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	public byte[] encrypt(String aesKey, String hmacKey, byte[] data) {
        byte [] encodeData = null;
    	try {	
    		Cipher AES_CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding");

    		//Process Changed https://stackoverflow.com/a/12039611/5319007
			byte[] encodedKey     = Base64.decode(aesKey, Base64.DEFAULT);
			SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

    		if(secretKey == null){
    			return null;
    		}
			byte[] randomIv = new byte[16];
			nextBytes(randomIv);
			
			AES_CIPHER.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(randomIv));
        	final byte[] encryptedMessage = AES_CIPHER.doFinal(data);
        	
        	final byte[] sign = sign(hmacKey, encryptedMessage);
        	if(sign == null){
        		return null;
        	}
        	encodeData = new byte[sign.length + encryptedMessage.length + randomIv.length];
        	System.arraycopy(randomIv, 0, encodeData,0, randomIv.length);
        	System.arraycopy(sign, 0, encodeData, randomIv.length, sign.length);
        	System.arraycopy(encryptedMessage, 0, encodeData, randomIv.length + sign.length, encryptedMessage.length);
        	return encodeData;
    	}catch (Exception e) {
    		e.printStackTrace();
        	return null;
        }
    }

	public Key generateSecretKey(byte[] keyBytes)  {
		SecretKey aesKey = null;
		try {
			aesKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
		}finally {
		    return aesKey;
		}
	}
	
	public SecretKeySpec generateHMAC(byte[] keyByte) {
		if(keyByte == null){
			return null;
		}
		SecretKeySpec signingKey = new SecretKeySpec(keyByte, "HMAC-SHA-1");
		return signingKey;
	}
	
	public byte[] sign(String hmacKey, byte[] data) {
        byte [] signData = null;
    	try {
			byte[] encodedKey     = Base64.decode(hmacKey, Base64.DEFAULT);
			SecretKey signingKey = generateHMAC(encodedKey);
            if(signingKey == null){
            	return null;
            }
    		Mac	senderMac = Mac.getInstance("HmacSHA1");
    		senderMac.init(signingKey);
    		signData = senderMac.doFinal(data);
    		return signData;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	} finally{
    		
    	}
    }
	
	public byte[] decrypt(String aesKey, String hmacKey, byte[] data) {
        byte[] decryptByte = null;
        try {
	        byte[] iv = Arrays.copyOfRange(data, 0, 16);
	        byte[] sentSignature = Arrays.copyOfRange(data, 16, 16+20);
	        byte[] encryptedMessage = Arrays.copyOfRange(data, 16+20, data.length);
	        
	        if (!verifySignature(hmacKey, sentSignature, encryptedMessage)){
	            return decryptByte;
	        }

			byte[] encodedKey = Base64.decode(aesKey, Base64.DEFAULT);
			SecretKey secretKey = (SecretKey) generateSecretKey(encodedKey);
	        if(secretKey == null){
	        	return decryptByte;
	        }
	        
	        Cipher AES_CIPHER = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            AES_CIPHER.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            decryptByte = AES_CIPHER.doFinal(encryptedMessage);
            return decryptByte;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        } 
    }

	private boolean verifySignature(String hmacKey, byte[] sentSignature, byte[] data) {
    	try {
			byte[] encodedKey = Base64.decode(hmacKey, Base64.DEFAULT);
			SecretKey signingKey = generateHMAC(encodedKey);
            if(signingKey == null){
            	return false;
            }
    		Mac	senderMac = Mac.getInstance("HmacSHA1");
    		senderMac.init(signingKey);
    		return Arrays.equals(sentSignature, senderMac.doFinal(data));
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	} finally{
    	}
    }

	private static SecureRandom secureRandom = null;
    static {
        try {
        	secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
        } 
    }

     private void nextBytes(byte[] bytes) {
        secureRandom.nextBytes(bytes);
    }
     
     
     public static String send(String Activation_Code, String datos_post) throws IOException {
         URL url = new URL("https://mw-uat.pagofon.net/securerest");
         Map<String, Object> params = new LinkedHashMap<>();
  
         params.put("ActivationCode", Activation_Code);
         params.put("Data", datos_post);
  
         StringBuilder postData = new StringBuilder();
         for (Map.Entry<String, Object> param : params.entrySet()) {
             if (postData.length() != 0)
                 postData.append('&');
             postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
             postData.append('=');
             postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                     "UTF-8"));
         }
         byte[] postDataBytes = postData.toString().getBytes("UTF-8");
         System.out.println("Peticion POST: "+new String (postData.toString() ));
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Content-Type",
                 "application/x-www-form-urlencoded");
         conn.setRequestProperty("Content-Length",
                 String.valueOf(postDataBytes.length));
         conn.setDoOutput(true);
         conn.getOutputStream().write(postDataBytes);
         String response = "";
         Reader in = new BufferedReader(new InputStreamReader(
                 conn.getInputStream(), "UTF-8"));
         
         for (int c = in.read(); c != -1; c = in.read()){
             //System.out.print((char) c);
             response = response+(char) c;
         }
		return response;
     }
}