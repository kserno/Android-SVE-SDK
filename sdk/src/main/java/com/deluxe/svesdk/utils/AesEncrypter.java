package com.deluxe.svesdk.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by filipsollar on 23.1.18.
 */

public class AesEncrypter {
    public static byte[] encryptPlaintext(String plaintext, String keyID) {
        byte[] byteKeyID;
        byte[] bytePlaintext;
        try {
            byteKeyID = keyID.getBytes("UTF-8");
            bytePlaintext = plaintext.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return encryptPlaintext(bytePlaintext, byteKeyID);
    }

    public static byte[] encryptPlaintext(byte[] plaintext, byte[] byteKeyID) {
        byte[] encryptedData;
        try {
//			Logger.log("AesSecurity.encryptFromUid", "start = "+plaintext+"<end_mark>");

            byte[] key = generateSecretKey(byteKeyID);
            // encrypt
            try {
                encryptedData = encrypt(key,plaintext);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

//			Logger.log("AesSecurity.encryptFromUid", "end result = "+encryptedData+"<end_mark>");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return encryptedData;
    }

    public static String decryptCryptotextAsString(byte[] cryptotext, String keyID) {
        byte[] byteKeyID;
        try {
            byteKeyID = keyID.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return decryptCryptotextAsString(cryptotext, byteKeyID);
    }

    public static byte[] decryptCryptotext(byte[] cryptotext, byte[] byteKeyID) {
        byte[] decryptedData;
        try {
//			Logger.log("AesSecurity.decryptFromUid", "start = "+cryptotext+"<end_mark>");
            byte[] key = generateSecretKey(byteKeyID);
            // decrypt
            decryptedData = decrypt(key,cryptotext);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return decryptedData;
    }

    public static String decryptCryptotextAsString(byte[] cryptotext, byte[] byteKeyID) {
        String result = null;
        try {
//			Logger.log("AesSecurity.decryptFromUid", "end result = "+decryptedData+"<end_mark>");
            result = new String(decryptCryptotext(cryptotext, byteKeyID), "UTF-8");
//			Logger.log("AesSecurity.decryptFromUid", "end result = "+result+"<end_mark>");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private static byte[] generateSecretKey(byte[] byteKeyID) throws Exception {
        KeyGenerator kgen = null;
        SecureRandom sr = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        sr.setSeed(byteKeyID);
        kgen.init(128, sr); // 128, 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
}
