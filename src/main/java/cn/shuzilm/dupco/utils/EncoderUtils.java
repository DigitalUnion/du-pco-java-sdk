package cn.shuzilm.dupco.utils;

public class EncoderUtils {

    /**
     * 加密方法,采用zlib压缩aes加密方式
     * @param data 明文
     * @param key  密钥
     * @return  密文
     * @throws Exception
     */
    public static byte[] encode(byte[] data,byte[] key) throws Exception {
        byte[] compress = Zlib.compress(data);
        return AesUtils.aesEncrypt(compress, key);
    }

    /**
     * 解密方法
     * @param data 密文
     * @param key  密钥
     * @return  明文
     * @throws Exception
     */
    public static byte[] decode(byte[] data,byte[] key) throws Exception {
        byte[] decrypt = AesUtils.aesDecrypt(data, key);
        return Zlib.decompress(decrypt);
    }
}
