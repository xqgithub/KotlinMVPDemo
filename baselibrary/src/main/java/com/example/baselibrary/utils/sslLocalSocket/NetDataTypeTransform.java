package com.example.baselibrary.utils.sslLocalSocket;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2019/9/12.
 * 数据转换的类
 */
public class NetDataTypeTransform {
    public static final String coding = "GB2312"; //全局定义，以适应系统其他部分

    public NetDataTypeTransform() {

    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     */
    public byte[] IntToByteArray(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * byte数组转化为int
     * 将低字节在前转为int，高字节在后的byte数组
     */
    public int ByteArrayToInt(byte[] bArr) {
        if (bArr.length != 4) {
            return -1;
        }
        return (int) ((((bArr[3] & 0xff) << 24)
                | ((bArr[2] & 0xff) << 16)
                | ((bArr[1] & 0xff) << 8) | ((bArr[0] & 0xff) << 0)));
    }

    /**
     * 将byte数组转化成String,为了支持中文，转化时用GBK编码方式
     */
    public String ByteArraytoString(byte[] valArr, int maxLen) {
        String result = null;
        int index = 0;
        while (index < valArr.length && index < maxLen) {
            if (valArr[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, 0, temp, 0, index);
        try {
            result = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将String转化为byte,为了支持中文，转化时用GBK编码方式
     */
    public byte[] StringToByteArray(String str) {
        byte[] temp = null;
        try {
            temp = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return temp;
    }

}
