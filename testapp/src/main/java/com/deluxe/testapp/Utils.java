package com.deluxe.testapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

/**
 * Created by filipsollar on 23.1.18.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static String getMacAddress(Context context) {
        String macAddress = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.isWifiEnabled()) {
            // WIFI ALREADY ENABLED. GRAB THE MAC ADDRESS HERE
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info.getMacAddress() != null) {
                macAddress = info.getMacAddress();
            }
        } else {
            // ENABLE THE WIFI FIRST
            wifiManager.setWifiEnabled(true);

            // WIFI IS NOW ENABLED. GRAB THE MAC ADDRESS HERE
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info.getMacAddress() != null) {
                macAddress = info.getMacAddress();

            }

            // NOW DISABLE IT AGAIN
            wifiManager.setWifiEnabled(false);
        }

        if (macAddress.equals("02:00:00:00:00:00")) {
            Log.d(TAG, "Fake MAC returned");
            macAddress = getMacAddr();
        }
        return macAddress;
    }

    public static String getIMEI(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        if (tm != null && tm.getDeviceId() != null) {
            return tm.getDeviceId();
        }


        return "";
    }

    public static String getSerial(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            return tm.getSimSerialNumber();
        }
        return "";
    }


    private static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return getRandomMAC();
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X",b) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getRandomMAC();
    }

    private static String getRandomMAC() {
        final SecureRandom random=new SecureRandom();
        String res = "";
        for( int i=0; i < 6; i++)
        {
            int ran = random.nextInt(255);
            String hx = String.format("%02X",ran);
            res += hx;
            if( i < 5 ) res += ":";
        }

        return res;
    }
}
