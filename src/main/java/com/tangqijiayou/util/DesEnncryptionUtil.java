package com.tangqijiayou.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;


public class DesEnncryptionUtil {

    private static final String ALGORITHM = "DES";
    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";
    private static final byte[] iv = {1,2,3,4,5,6,7,8}; 
    private static final IvParameterSpec zeroIv = new IvParameterSpec(iv);  
    private static String keyStr = "jspQ4BiPj4ztU5cLiNtM9g==";
//    private static String keyStr = "123456789";
    private static Key key = null;

    /**
     * 创建密钥
     * @param strKey
     */
    @SuppressWarnings("unused")
    private static void createKey(String strKey){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(new SecureRandom(strKey.getBytes()));
            key = kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 初始化密鈅
     * @throws IOException
     * @throws Exception
     */
    private static void initKey(){
        try {
            key = toKey(keyStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**  
     * 转换密钥
     *    
     * @param key   
     * @return   
     * @throws Exception   
     */   
    private static Key toKey(byte[] key) throws Exception {    
        DESKeySpec dks = new DESKeySpec(key);    
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);    
        SecretKey secretKey = keyFactory.generateSecret(dks);    
        return secretKey;    
    }  

    /**
     * 加密String明文输入,String密文输出
     * 
     * @param strContent
     * @return
     */
    public static String getEncString(String strContent) {
        byte[] byteCipherContent = null;
        byte[] byteContent = null;
        String strCipherContent = "";
        try {
            byteContent = strContent.getBytes("UTF8");
            byteCipherContent = getEncCode(byteContent);
            strCipherContent = DESBase64.encode(byteCipherContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteContent = null;
            byteCipherContent = null;
        }
        return strCipherContent;
    }

    /**
     * 解密 以String密文输入,String明文输出
     * 
     * @param strCipherContent
     * @return
     */
    public static String getDesString(String strCipherContent) {
        byte[] byteContent = null;
        byte[] byteCipherContent = null;
        String strContent = "";
        try {
            byteCipherContent = DESBase64.decode(strCipherContent);
            byteContent = getDesCode(byteCipherContent);
            strContent = new String(byteContent, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteContent = null;
            byteCipherContent = null;
        }
        return strContent;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * 
     * @param byteContent
     * @return
     */
    private static byte[] getEncCode(byte[] byteContent) {
        if(key == null){
            initKey();
        }
        byte[] byteCipherContent = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byteCipherContent = cipher.doFinal(byteContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteCipherContent;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * 
     * @param byteCipherContent
     * @return
     */
    private static byte[] getDesCode(byte[] byteCipherContent) {
        if(key == null){
            initKey();
        }
        Cipher cipher;
        byte[] byteContent = null;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byteContent = cipher.doFinal(byteCipherContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteContent;

    }

    /**
     * 判断String是否能转换为整数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


}
