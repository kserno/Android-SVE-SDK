package com.deluxe.svesdk.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class.
 *
 * Helper for AES encryption.
 */
public class AesEncrypter {

    /**
     * Encryption of string plaintext input using string encryption key
     * @param plaintext Input plaintext
     * @param keyID     Input key
     * @return Encrypted plaintext as byte[]
     */
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

    /**
     * Encryption of byte[] plaintext input using byte[] encryption key
     * @param plaintext Input plaintext
     * @param byteKeyID Input key
     * @return Encrypted plaintext as byte[]
     */
    public static byte[] encryptPlaintext(byte[] plaintext, byte[] byteKeyID) {
        byte[] encryptedData;
        try {
            byte[] key = generateSecretKey(byteKeyID);
            // encrypt
            try {
                encryptedData = encrypt(key,plaintext);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return encryptedData;
    }

    /**
     * Decryption of byte[] plaintext input using string encryption key
     * @param cryptotext    Input encrypted text
     * @param keyID         Input key
     * @return Decrypted plaintext
     */
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

    /**
     * Decryption of byte[] plaintext input using byte[] encryption key
     * @param cryptotext    Input encrypted text
     * @param byteKeyID     Input key
     * @return Decrypted plaintext as byte[]
     */
    public static byte[] decryptCryptotext(byte[] cryptotext, byte[] byteKeyID) {
        byte[] decryptedData;
        try {
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

    /**
     * Decryption of byte[] plaintext input using byte[] encryption key
     * @param cryptotext    Input encrypted text
     * @param byteKeyID     Input key
     * @return Decrypted plaintext as string
     */
    public static String decryptCryptotextAsString(byte[] cryptotext, byte[] byteKeyID) {
        String result = null;
        try {
            result = new String(decryptCryptotext(cryptotext, byteKeyID), "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * Generates AES secret key from input byte[] key id
     * @param byteKeyID Input key
     * @return secret key
     * @throws Exception
     */
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

    /**
     * Encyption
     * @param raw   Key
     * @param clear Plaintext
     * @return Encrypted plaintext
     * @throws Exception
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * Decryption
     * @param raw       Key
     * @param encrypted Encrypted plaintext
     * @return Decrypted plaintext
     * @throws Exception
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
}
