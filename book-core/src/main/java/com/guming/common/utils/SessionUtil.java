package com.guming.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
public class SessionUtil {
    private static final int SESSION_ID_BYTES = 16;

    public static Logger logger =LoggerFactory.getLogger(SessionUtil.class);

    public static synchronized String generateSessionId() {
        try {
            // Generate a byte array containing a session identifier
            Random random = new SecureRandom();  // 取随机数发生器, 默认是SecureRandom
            byte bytes[] = new byte[SESSION_ID_BYTES];
            random.nextBytes(bytes); //产生16字节的byte
            bytes = getDigest().digest(bytes); // 取摘要,默认是"MD5"算法
            // Render the result as a String of hexadecimal digits
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {     //转化为16进制字符串
                byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
                byte b2 = (byte) (bytes[i] & 0x0f);
                if (b1 < 10) {
                    result.append((char) ('0' + b1));
                } else {
                    result.append((char) ('A' + (b1 - 10)));
                }
                if (b2 < 10) {
                    result.append((char) ('0' + b2));
                } else {
                    result.append((char) ('A' + (b2 - 10)));
                }
            }
            return (result.toString());
        } catch (NoSuchAlgorithmException e) {
            logger.error("",e);
        }
        return null;
    }

    private static MessageDigest getDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }
}
