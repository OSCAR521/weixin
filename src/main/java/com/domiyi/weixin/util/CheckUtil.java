package com.domiyi.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by  OSCAR on 2018/12/19'
 * 验证微信token
 */
public class CheckUtil {

    private static final String token = "Xinglongbing";

    /**
     * 把拼接后的字符串和signature进行比较
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String[] arr = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arr);

        //用StringBuffer进行字符串拼接，后期要toString()转换为String类型
        StringBuffer content = new StringBuffer();
        for (int i =0;i<arr.length;i++){
            content.append(arr[i]);
        }
        System.out.println(content);

        //sha1加密
        String temp = getSha1(content.toString());

        return temp.equals(signature);
    }

    /**
     * sha1网上加密的算法
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getSha1(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (null == str || str.length() == 0){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
