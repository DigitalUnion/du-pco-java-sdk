package cn.shuzilm.dupco;

import cn.shuzilm.dupco.utils.EncoderUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Receiver {

    /**
     *
     * @param request receiver HttpServletRequest
     * @param secret secret
     * @retur decode data
     * @throws Exception
     */
    public static byte[] DecodeRequestData(HttpServletRequest request,byte[] secret) throws Exception {
        int length = request.getContentLength();
        InputStream inputStream = request.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] bytes = new byte[length];
        dataInputStream.readFully(bytes);
        dataInputStream.close();
        byte[] base64Decode = Base64.getDecoder().decode(bytes);
        return EncoderUtils.decode(base64Decode, secret);
    }

    /**
     *
     * @param data receiver base64 encode data
     * @param secret secret
     * @return  decode data
     * @throws Exception
     */
    public static byte[] DecodeData(String data,byte[] secret) throws Exception {
        byte[] base64Decode = Base64.getDecoder().decode(data);
        return EncoderUtils.decode(base64Decode, secret);
    }
}
