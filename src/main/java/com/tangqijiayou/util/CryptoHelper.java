package com.tangqijiayou.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 加解密的helper function
 * @author leslie
 *
 */
final public class CryptoHelper {
	static byte[] TDES_KEY;
	static byte[] TDES_IV;
	public static long key = 1111111L;
	
	static {
		String str="347234jvklx,mem,()_&^@%@nd ,mcxvsf90fksVXREWWER";
		byte[] tmp=sha1(str.getBytes());
		TDES_KEY=Arrays.copyOf(tmp, 24);
		TDES_IV=Arrays.copyOf(tmp, 8);
	}

	/**
	 * HmacSHA1运算
	 * @param key 密钥
	 * @param data 要生成mac的数据
	 * @return
	 */
	public static byte[] hmacSha1(byte[] key, byte[] data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");

			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * sha1运算
	 * @param buf
	 * @return
	 */
	public static byte[] sha1(byte []buf) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			return md.digest(buf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * base64编码
	 * @param buf
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String base64Encode(byte []buf) {
		return new BASE64Encoder().encode(buf);
	}
	
	/**
	 * base64解码
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static byte[] base64Decode(String str) {
		try {
			return new BASE64Decoder().decodeBuffer(str);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param iv
	 * @param buf
	 * @param encrypt true表示进行加密运算，false表示进行解密
	 * @return
	 */
	static byte[] tripleDes(byte []key,byte []iv,byte []buf,boolean encrypt) {
		if ((key.length!=24) || (iv.length!=8))
			throw new IllegalArgumentException();
		
		try {
			DESedeKeySpec keySpec = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey secretKey = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			if (encrypt)
				cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));
			else cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(iv));

			return cipher.doFinal(buf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 使用3DES加密，pkcs7(5),cbc
	 * @param key key是24字节的数组
	 * @param iv iv必须是8字节的数组
	 * @param buf
	 * @return
	 */
	public static byte[] tripleDesEncrypt(byte []key, byte []iv,byte []buf) {
		return tripleDes(key,iv,buf,true);
	}

	/**
	 * 使用3des解密，pkcs7(5),cbc
	 * @param key key长24字节
	 * @param iv iv长8字节
	 * @param buf
	 * @return
	 */
	public static byte[] tripleDesDecrypt(byte []key, byte []iv,byte []buf) {
		return tripleDes(key,iv,buf,false);
	}

	/**
	 * 对 str 使用 base64(3des(str)) 加密，编码为 utf-8。密钥为 TDES_KEY, TDES_IV
	 * @param str
	 * @return
	 */
	public static String tripleDesEncrypt(String str) {
		try {
			byte[] buf = str.getBytes("UTF-8");
			buf = tripleDesEncrypt(TDES_KEY, TDES_IV, buf);
			return base64Encode(buf);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对 str 按 base64(3des(str)) 解密，编码为 utf-8。密钥为 TDES_KEY, TDES_IV
	 * @param str
	 * @return
	 */
	public static String tripleDesDecrypt(String str) {
		try {
			byte[] buf = base64Decode(str);
			buf = tripleDesDecrypt(TDES_KEY, TDES_IV, buf);
			return new String(buf, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对 str 使用 hex(3des(str)) 加密，编码为 utf-8。密钥为 TDES_KEY, TDES_IV
	 * @param str
	 * @return
	 */
	public static String tripleDesEncryptHex(String str) {
		try {
			byte[] buf = str.getBytes("UTF-8");
			buf = tripleDesEncrypt(TDES_KEY, TDES_IV, buf);
			return bytes2hex(buf);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对 str 按 hex(3des(str)) 解密，编码为 utf-8。密钥为 TDES_KEY, TDES_IV
	 * @param str
	 * @return
	 */
	public static String tripleDesDecryptHex(String str) {
		try {
			byte[] buf = hex2bytes(str);
			buf = tripleDesDecrypt(TDES_KEY, TDES_IV, buf);
			return new String(buf, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把byte[] 转换成十六进制表示的字符串
	 * @param buf
	 * @return
	 */
	public static String bytes2hex(byte[] buf) {
		StringBuilder sb=new StringBuilder(buf.length*2);

	    for (int i=0;i<buf.length;i++) {
	        String tmp=(Integer.toHexString(buf[i] & 0xff));
	        if (tmp.length()==1) sb.append("0");    //补足2位
		    sb.append(tmp);
	    }
	    return sb.toString();
	}

	/**
	 * 把十六进制表示的字符串转换成byte[]
	 * @param str
	 * @return
	 */
	public static byte[] hex2bytes(String str) {
		if (str.length()%2!=0) throw new IllegalArgumentException();
		
		byte []buf=new byte[str.length()/2];

		for (int i=0;i<buf.length;i++) {
			String s=str.substring(i*2, i*2+2);
			buf[i]=Integer.valueOf(s, 16).byteValue();
		}
		return buf;
	}

	/**
	 * 使用 base64(sha1(str)) 编码
	 * @param str
	 * @return
	 */
	public static String sha1String(String str) {
		try {
			return base64Encode(sha1(str.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getSecuretPassword(String password, long accountId) {
		return sha1String(password + accountId);
	}
	

}
