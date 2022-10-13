package cn.shuzilm.dupco.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Zlib {
    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compressor = new Deflater();
        compressor.reset();
        compressor.setInput(data);
        compressor.setLevel(4);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compressor.finished()) {
                int i = compressor.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("zlib compress error"+e);
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compressor.end();
        return output;
    }

    public static byte[] decompress(byte[] data){
        byte[] output = new byte[0];
        Inflater decompressor = new Inflater();
        decompressor.reset();
        decompressor.setInput(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try{
            byte[] buf = new  byte[1024];
            while (!decompressor.finished()){
                int i = decompressor.inflate(buf);
                bos.write(buf,0,i);
            }
            output = bos.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("Zlib decompress error"+e.getMessage());
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        decompressor.end();
        return output;
    }
}
