package cn.shuzilm.dupco.utils;

public class EncoderUtils {
    public static byte[] encode(byte[] data,byte[] key) throws Exception {
        byte[] compress = Zlib.compress(data);
        return AesUtils.aesEncrypt(compress, key);
    }

    public static byte[] decode(byte[] data,byte[] key) throws Exception {
        byte[] decrypt = AesUtils.aesDecrypt(data, key);
        return Zlib.decompress(decrypt);
    }
}
