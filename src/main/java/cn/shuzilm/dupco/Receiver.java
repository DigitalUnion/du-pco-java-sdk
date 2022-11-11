package cn.shuzilm.dupco;

import cn.shuzilm.dupco.utils.EncoderUtils;

import java.util.Base64;

public class Receiver {

    /**
     *
     * @param data receiver base64 encode data
     * @param secret secret
     * @return  decode data
     */
    public static byte[] DecodeData(String data,byte[] secret) throws Exception {
        byte[] base64Decode = Base64.getDecoder().decode(data);
        return EncoderUtils.decode(base64Decode, secret);
    }
}
