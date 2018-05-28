package com.guming.common.utils;

import com.guming.common.base.dto.TokenInfoDto;
import com.mysql.jdbc.util.Base64Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: PengCheng
 * @Description: 加解密工具类
 * @Date: 2018/4/12
 */
public class EncryptUtils {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    public static TokenInfoDto decryptTokenPassInfo(String encryptInfo) {
        TokenInfoDto tokenInfoDto= new TokenInfoDto();
        String decryptInfo = new String(Base64Decoder.decode(encryptInfo.getBytes(),0,encryptInfo.length()));
        String[] infos = decryptInfo.split("-");
        tokenInfoDto.setUserName(infos[0]);
        tokenInfoDto.setToken(infos[1]);
        String pass = "";
        for (int i=2;i<infos.length;i++){
            pass += infos[i];
        }
        tokenInfoDto.setUserPass(pass);
        return tokenInfoDto;
    }

    /**
     * SHA-1 hash加密算法
     * @param bytes
     * @return
     */
    public static String hashSHAEncrypt(byte[] bytes){
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(bytes);
            return bytesToHex(sha1.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("",e);
        }
        return null;
    }

    public static String bytesToHex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
