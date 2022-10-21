package cn.shuzilm.dupco.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;

public class AesUtils {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static byte[] aesEncrypt(byte[] data, byte[] key) throws Exception{
        if (key == null) {
            return null;
        }
        byte[] raw = fillKey(key);
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);//"算法/模式/补码方式"
        byte[] iv = new byte[cipher.getBlockSize()];
        System.arraycopy(raw,0,iv,0,cipher.getBlockSize());
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,ivSpec);
        return cipher.doFinal(data);
    }

    public static byte[] aesDecrypt(byte[] data, byte[] key) throws Exception {
        // 判断Key是否正确
        if (key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] raw = fillKey(key);
        byte[] iv = new byte[cipher.getBlockSize()];
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        System.arraycopy(raw,0,iv,0,cipher.getBlockSize());
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec,paramSpec);
        return cipher.doFinal(data);
    }

    private static byte[] fillKey(byte[] key) throws IOException {
        int l = key.length;
        if (l == 16) {
            return key;
        }
        if (l < 16) {
            return fillN(key,16);
        }
        byte[] bytes = new byte[16];
        System.arraycopy(key,0,bytes,0,16);
        return bytes;
    }

    private static byte[] fillN(byte[] s, int count) throws IOException {
        int l = s.length;
        int c = count / l;
        int m = count % l;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(count);
        for (int i = 0; i < c; i++) {
            buf.write(s);
        }
        if (m != 0) {
            byte[] bytes = new byte[m];
            System.arraycopy(s,0,bytes,0,m);
            buf.write(bytes);
        }
        return buf.toByteArray();
    }

}
