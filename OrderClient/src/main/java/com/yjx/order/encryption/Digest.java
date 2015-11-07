package com.yjx.order.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法
 * Created by bob on 20/12/13.
 */
public class Digest {

    /**
     * 对byte数组进行MD5摘要
     *
     * @param bytes
     * @return 密文
     */
    public final static byte[] md5(byte[] bytes) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        msgDigest.update(bytes);

        return msgDigest.digest();
    }

    /**
     * 对字符串进行 md5 摘要
     *
     * @param text
     * @return
     */
    public static String md5(String text) {
        return byte2HexString(md5(text.getBytes()));
    }

    /**
     * 对文件进行 md5
     * @param file
     * @return
     * */
    public static String md5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byte2HexString(b);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }

    /**
     * byte2HexString
     * byte 转 16进制
     */
    private static String byte2HexString(byte[] tmp) {
        String s;
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        s = new String(str);
        return s;
    }

    /**
     * 对文件全文生成MD5摘要
     *
     * @param file
     * 要加密的文件
     * @return MD5摘要码
     */

    public final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}

