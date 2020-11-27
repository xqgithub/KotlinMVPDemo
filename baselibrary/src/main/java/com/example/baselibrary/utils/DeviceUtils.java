package com.example.baselibrary.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.example.baselibrary.application.MyApplication;
import com.facebook.stetho.common.LogUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * desc  : 设备相关工具类
 */
public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(
                MyApplication.myapplication.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) MyApplication.myapplication.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     *
     * @return MAC地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
        if (result.result == 0) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    if (result.successMsg != null) {
                        return result.successMsg;
                    }
                }
            }
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 关机
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void shutdown() {
        ShellUtils.execCmd("reboot -p", true);
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.myapplication.getApplicationContext().startActivity(intent);
    }

    /**
     * 重启
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void reboot() {
        ShellUtils.execCmd("reboot", true);
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        MyApplication.myapplication.getApplicationContext().sendBroadcast(intent);
    }

    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     * @param reason 传递给内核来请求特殊的引导模式，如"recovery"
     */
    public static void reboot(String reason) {
        PowerManager mPowerManager = (PowerManager) MyApplication.myapplication.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        try {
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启到recovery
     * <p>需要root权限</p>
     */
    public static void reboot2Recovery() {
        ShellUtils.execCmd("reboot recovery", true);
    }

    /**
     * 重启到bootloader
     * <p>需要root权限</p>
     */
    public static void reboot2Bootloader() {
        ShellUtils.execCmd("reboot bootloader", true);
    }

    /**
     * 获取设备的UUID
     *
     * @return
     */
    public static String getUUID() {
        Context context = MyApplication.myapplication.getApplicationContext();
        UUID uuid = null;
        // Android SOCKS_ACCOUNT_ID
        final String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId
                        .getBytes("utf8"));
            } else {
                // IMEI
                String deviceId = getUserIMEI().get(0);

                if (StringUtils.isEmpty(deviceId)) {
                    // MAC
                    @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) context
                            .getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    deviceId = info.getMacAddress().replace(":", "");
                }

                uuid = deviceId != null ?
                        UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) :
                        UUID.randomUUID();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
//        LogUtils.i("UUID----->" + uuid.toString());
//        System.out.println("UUID----->" + uuid.toString());
        return uuid.toString();
    }


    /**
     * 通过方法名来获取方法的参数列表
     *
     * @param methodName
     * @return
     */
    public static Class[] getMethodParamTypes(String methodName) {
        Class[] params = null;
        try {
            Method[] methods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i].getName())) {
                    params = methods[i].getParameterTypes();
                    if (params.length >= 1) {
                        LogUtil.d("length:", "" + params.length);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.d("", e.toString());
        }
        return params;
    }

    /**
     * @param subId      subId为卡Id，相当于在手机卡插到手机上时，系统为卡分配的一个标识Id 通过getSubId方法获取
     * @param methodName 方法名
     * @param context
     * @return
     */
    public static Object getPhoneInfo(int subId, String methodName, Context context) {
        Object value = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 21) {
                Method method = tm.getClass().getMethod(methodName, getMethodParamTypes(methodName));
                if (subId >= 0) {
                    value = method.invoke(tm, subId);
                }
            }
        } catch (Exception e) {
            LogUtil.d("", e.toString());
        }
        return value;
    }

    /**
     * 获取手机的IMEI号，该方法用于手机5.0以后
     *
     * @param context
     * @param soltId  slotId为卡槽Id，它的值为 0、1
     * @return
     */
    public static String getDeviced(Context context, int soltId) {
        return (String) getPhoneInfo(soltId, "getDeviceId", context);
    }

    /**
     * @param context
     * @param slotId  slotId为卡槽Id，它的值为 0、1
     * @return
     */
    public static int getSubId(Context context, int slotId) {
        Uri uri = Uri.parse("content://telephony/siminfo");
        Cursor cursor = null;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            cursor = contentResolver.query(uri, new String[]{"_id", "sim_id"}, "sim_id = ?", new String[]{String.valueOf(slotId)}, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndex("_id"));
                }
            }
        } catch (Exception e) {
            LogUtil.d("getSubId", e.toString());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return -1;
    }

    /**
     * 获取手机的IMSI号 国际移动用户识别码 用于唯一识别移动用户的一个号码 在GSM网络，这个号码通常被存放在SIM卡中 这个号码通常被存放在SIM卡中
     * 该方法用于手机5.0以后
     *
     * @param context
     * @param subId
     * @return
     */
    public static String getSubscriberId(Context context, int subId) {
        String imsi = (String) getPhoneInfo(subId, "getSubscriberId", context);
        return imsi;
    }


    /**
     * 获得手机的IMEI号，该方法用于手机5.0以前
     *
     * @param context
     * @return
     */
    public static String getimei(Context context) {
        @SuppressLint("MissingPermission") String deviceId = ((TelephonyManager)
                context.getSystemService(
                        Context.TELEPHONY_SERVICE)).getDeviceId();
        return deviceId;
    }

    /**
     * 获得手机的IMSI号，该方法用于手机5.0以前
     *
     * @param context
     * @return
     */
    public static String getimsi(Context context) {
        @SuppressLint("MissingPermission") String imsi = ((TelephonyManager)
                context.getSystemService(
                        Context.TELEPHONY_SERVICE)).getSubscriberId();

        return imsi;
    }


    /**
     * 获取 用户的IMEI(MEID)号
     *
     * @return
     */
    public static List<String> getUserIMEI() {
        List<String> list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 21) {//5.0以后
            String imei1 = getDeviced(MyApplication.myapplication.getApplicationContext(), 0);//第一个插槽
            String imei2 = getDeviced(MyApplication.myapplication.getApplicationContext(), 1);//第二个插槽
            if (StringUtils.isBlank(imei1) && StringUtils.isBlank(imei2)) {
                String imei3 = getimei(MyApplication.myapplication.getApplicationContext());
                if (StringUtils.isBlank(imei3)) {
                    list.add("");
                } else {
                    list.add(imei3);
                }
            } else {
                if (imei1.equals(imei2)) {//只有一个插槽
                    list.add(imei1);
                } else {
                    list.add(imei1);
                    list.add(imei2);
                }
            }
        } else {//5.0以后之前
            String imei1 = getimei(MyApplication.myapplication.getApplicationContext());
            if (StringUtils.isBlank(imei1)) {
                list.add("");
            } else {
                list.add(imei1);
            }
        }
        return list;
    }

    /**
     * 获取 用户的IMSI号
     */
    public static List<String> getUserIMSI() {
        List<String> list = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 21) {//5.0以后
            String sim1 = getSubscriberId(MyApplication.myapplication.getApplicationContext(), getSubId(MyApplication.myapplication.getApplicationContext(), 0));//第一个插槽
            String sim2 = getSubscriberId(MyApplication.myapplication.getApplicationContext(), getSubId(MyApplication.myapplication.getApplicationContext(), 1));//第二个插槽
            if (!StringUtils.isBlank(sim1)) {
                list.add(sim1);
            }
            if (!StringUtils.isBlank(sim2)) {
                list.add(sim2);
            }
            /**
             * 当前面两个都获取为空的时候，调用一次。因为有部分手机会用第一种方法获取不到
             */
            if (StringUtils.isBlank(sim1) && StringUtils.isBlank(sim2)) {
                String sim3 = getimsi(MyApplication.myapplication.getApplicationContext());
                if (!StringUtils.isBlank(sim3)) {
                    list.add(sim3);
                }
            }
        } else {
            String sim1 = getimsi(MyApplication.myapplication.getApplicationContext());
            if (!StringUtils.isBlank(sim1)) {
                list.add(sim1);
            }
        }
        return list;
    }

    /**
     * 00001
     * 获取DEVICE_ID,ANDROID_ID,MAC这三个值合并为一个字符串进行MD5作为设备唯一标识
     * 创建者 XQ  2018.01.09
     */
    public static String getUniqueDeviceID(Context context) {
        String uniqueID = "";
        String filePath0 = "";//保存文件路径0
        String filePath1 = "";//保存文件路径1
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                filePath0 = Environment.getExternalStorageDirectory() + File.separator + "SADeviceID" + File.separator + "7e8e9e123456.txt";
            }
            filePath1 = MyApplication.myapplication.getApplicationContext().getFilesDir() + File.separator + "SADeviceID" + File.separator + "7e8e9e123456.txt";

            File file0 = new File(filePath0);
            File file1 = new File(filePath1);

            if (file0.exists()) {
                //文件存在从文件中读取数据
                uniqueID = FileUtils.readFile2String(file0, "UTF-8");
                //存储到本地
                FileUtils.writeFileFromString(filePath0, uniqueID, false);
                FileUtils.writeFileFromString(filePath1, uniqueID, false);
                return uniqueID;
            } else if (file1.exists()) {
                //文件存在从文件中读取数据
                uniqueID = FileUtils.readFile2String(file1, "UTF-8");
                //存储到本地
                FileUtils.writeFileFromString(filePath0, uniqueID, false);
                FileUtils.writeFileFromString(filePath1, uniqueID, false);
                return uniqueID;
            } else {
                //两个文件都不存在，那就重新生成
                String device_id = getUserIMEI().get(0);
                if (StringUtils.isBlank(device_id)) {
                    device_id = "";
                }


                String androidid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
                        .toLowerCase();//统一使用小写，是否存在不同API获取的结果大小写不同的情况未知
                if (StringUtils.isBlank(androidid)) {
                    androidid = "";
                } else {
                    if ("9774d56d682e549c".equals(androidid)) {
                        androidid = "";
                    }
                }
                String mac = getMacAddress(context);

                uniqueID = device_id + androidid + mac;
                if (StringUtils.isBlank(uniqueID)) {
                    //为空的话生成随机字符串
                    uniqueID = getMd5(UUID.randomUUID().toString());
                } else {
                    uniqueID = getMd5(uniqueID);
                }
//                LogUtil.i("uniqueID----->" + uniqueID);
                //存储到本地
                FileUtils.writeFileFromString(filePath0, uniqueID, false);
                FileUtils.writeFileFromString(filePath1, uniqueID, false);
                return uniqueID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniqueID;
    }


    /**
     * 00001
     * 判断是否具备  ACCESS_WIFI_STATE  权限
     * 创建者 XQ  2018.01.09
     */
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
//            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:" + "access wifi state is enabled");
            return true;
        } else
            return false;
    }


    /**
     * 00001
     * 得到设备的MAC地址,6.0以下
     * 创建者 XQ  2018.01.09
     */
    public static String getMacAddress0(Context context) {
        if (Build.VERSION.SDK_INT < 23) {//6.0以下
            if (isAccessWifiStateAuthorized(context)) {
                WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = null;
                try {
                    wifiInfo = wifiMgr.getConnectionInfo();
                    return wifiInfo.getMacAddress();
                } catch (Exception e) {
                    Log.e("----->" + "NetInfoManager", "getMacAddressFirst:" + e.toString());
                }
            }
        }
        return "";
    }

    /**
     * 00001
     * 4.0一直到6.0，7.0系统都可以获取得到Mac地址
     * 创建者 XQ  2018.01.09
     */
    public static String getMacAddress1() {
        try {
//            Log.e("----->" + "NetInfoManager", "getMacAddressSecond");
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 00001
     * 综合获取mac地址
     * 创建者 XQ  2018.01.09
     */
    public static String getMacAddress(Context context) {
        String mac = getMacAddress0(context);
        if (mac.equals(null) || mac.equals("") || mac.equals("02:00:00:00:00:00")) {
            mac = getMacAddress1();
        }
        if (mac.equals("02:00:00:00:00:00")) {
            mac = "";
        }
        mac = mac.toLowerCase();
        return mac;
    }


    /**
     * 00001
     * md5处理MacAddress
     * 创建者 XQ  2018.01.09
     */
    private static String getMd5(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = (md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            //SDKLog.i("device id is " + hexValue.toString());
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /**
     * 获取手机的cpu型号
     */
    public static String getSupportedabis() {
        String abi = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            abi = Build.CPU_ABI;
        } else {
            abi = Build.SUPPORTED_ABIS[0];
        }
        return abi;
    }


}