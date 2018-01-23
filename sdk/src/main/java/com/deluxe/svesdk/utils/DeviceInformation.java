package com.deluxe.svesdk.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by filipsollar on 23.1.18.
 */

public class DeviceInformation {

    private static final String TAG = DeviceInformation.class.getSimpleName();

    private static final String ALLOWED_CHARACTERS_DID ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";



    public static String getStandardDeviceId(Context context,
                                             String tmDevice,
                                             String tmSerial,
                                             String macAddress) throws IllegalStateException {

        String androidId = "";


        androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d(TAG, "tmSerial="+tmSerial+" androidId="+androidId+" tmDevice="+tmDevice+" MAC="+macAddress);

        if (TextUtils.isEmpty(tmDevice) && TextUtils.isEmpty(macAddress)) {
            // Both values can not be null - throw exception
            throw new IllegalStateException("Both MAC and IMEI are missing. Cannot generate valid device ID.");
        }

        // if old way has to be restored, then comment from here

        // prefix
        byte[] bytePrefix = MACtobyteConverter("6E");

        // prefix
        byte[] bytePostfix = MACtobyteConverter("");//MACtobyteConverter("C5");

        // first transform MAC string to real hex value
        byte[] byteMac = MACtobyteConverter(macAddress);

        // transform IMEI to real hex value (Long.parseLong())
        byte[] byteIMEI = IMEItobyteConverter(tmDevice);

        // handle null values of both byteMAC and byteIMEI -> invalid state, throw exception
        if ((byteMac == null || byteMac.length<6) && (byteIMEI == null || byteIMEI.length<8)) {
            // Both values can not be null - throw exception
            throw new IllegalStateException("Both MAC and IMEI are missing. Cannot generate valid device ID.");
        }

        // handle if only one of MAC or IMEI is invalid
        if ((byteMac == null || byteMac.length<6)) {
            byteMac = new byte[6];
            for (int i=0; i< 6; i++) {
                byteMac[i] = byteIMEI[i];
            }
        }
        if ((byteIMEI == null || byteIMEI.length<8)) {
            byteIMEI = new byte[8];
            for (int i=0; i< 8; i++) {
                byteIMEI[i] = byteMac[i % 6];
            }
        }

        // concat MAC+IMEI
        Log.d(TAG, "l1="+byteMac.length+" l2="+byteIMEI.length);
        int size = byteMac.length + byteIMEI.length + bytePrefix.length + bytePostfix.length;
        Log.d(TAG, "combined length = "+size);
        byte[] byteCombined = new byte[size];
        for (int i=0; i< size; i++) {
            if (i<bytePrefix.length) {
                byteCombined[i] = bytePrefix[i];
            }
            else if (i<byteMac.length + bytePrefix.length) {
                byteCombined[i] = byteMac[i - (bytePrefix.length)];
            }
            else if (i<byteIMEI.length + byteMac.length + bytePrefix.length) {
                byteCombined[i] = byteIMEI[i - (byteMac.length + bytePrefix.length)];
            }
            else {
                byteCombined[i] = bytePostfix[i - (byteIMEI.length + byteMac.length + bytePrefix.length)];
            }
        }

        // create hex key
        byte[] byteKey = MACtobyteConverter("4D75B96C4295");

        // call AesEncrypter.encryptPlaintext() with some dummy key
        byte[] byteResult = AesEncrypter.encryptPlaintext(byteCombined, byteKey);

        // encode result in Base64
        byte[] base64result = Base64.encode(byteResult, Base64.NO_WRAP); // no wrap == no new line at the end (reported as bug)

        // transform base64 byte array to String
        String deviceIdFinal = "";
        try {
            deviceIdFinal = new String(base64result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Both MAC and IMEI are missing. Cannot generate valid device ID.");
        }
        deviceIdFinal = "A01_" + deviceIdFinal;

        if( deviceIdFinal.equals("A01_BbGgPZSs/K0ZDofsiiX86g==") ||
                deviceIdFinal.equals("A01_tYPJSgs9iZ4qc/u9qTnjIg=="))
        {
            Log.d(TAG, "device id is wrong");
            deviceIdFinal = getRandomDID();
        }

        Log.d(TAG, "deviceIdFinal="+deviceIdFinal);

        return deviceIdFinal;
    }

    /**
     * Transforms MAC address in 00:11:22:33:44:55 format to byte array representation
     * @param MAC
     * @return
     */
    private static byte[] MACtobyteConverter(String MAC) {
        // first remove all ":" from MAC address
        MAC = MAC.replaceAll(":", "");

        // now convert to byte array
        int len = MAC.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(MAC.charAt(i), 16) << 4)
                    + Character.digit(MAC.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Transforms IMEI address format to byte array representation
     * @param IMEI
     * @return
     */
    private static byte[] IMEItobyteConverter(String IMEI) {
        // now convert to byte array
        long imeiInLong;
        try {
            imeiInLong = Long.parseLong(IMEI);
        }
        catch (NumberFormatException e) {
            Log.w(TAG, "Can't convert IMEI to byte, Illegal number format");
            return null;
        }
        byte[] data = ByteBuffer.allocate(8).putLong(imeiInLong).array();
        return data;
    }

    private static String getRandomDID() {
        final SecureRandom random = new SecureRandom();
        final StringBuilder sb = new StringBuilder(22);
        for(int i = 0; i < 22; ++i) {
            sb.append(ALLOWED_CHARACTERS_DID.charAt(random.nextInt(ALLOWED_CHARACTERS_DID.length())));
        }
        return "A01_"+sb.toString()+"=R";
    }


}