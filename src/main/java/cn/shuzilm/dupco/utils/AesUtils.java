package cn.shuzilm.dupco.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;

public class AesUtils {

    public static byte[] aesEncrypt(byte[] data, byte[] key) throws Exception{
        if (key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        byte[] raw = fillKey(key);
        byte[] iv = new byte[cipher.getBlockSize()];
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        System.arraycopy(raw,0,iv,0,cipher.getBlockSize());
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,paramSpec);
        return cipher.doFinal(data);
        //return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static byte[] aesDecrypt(byte[] data, byte[] key) throws Exception {
        // 判断Key是否正确
        if (key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
        switch (l) {
            case 16,24,32:
                return key;
        }
        if (l < 16) {
            return fillN(key,16);
        }
        if (l < 24) {
            return fillN(key,24);
        }
        if (l < 32) {
            return fillN(key,32);
        }
        byte[] bytes = new byte[32];
        System.arraycopy(key,0,bytes,0,32);
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
