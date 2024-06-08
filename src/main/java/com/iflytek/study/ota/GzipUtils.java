package com.iflytek.study.ota;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzis = new GZIPInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(compressedData.length);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzis.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }

        gzis.close();
        baos.close();

        return baos.toByteArray();
    }

    public static byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(input);
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

}
