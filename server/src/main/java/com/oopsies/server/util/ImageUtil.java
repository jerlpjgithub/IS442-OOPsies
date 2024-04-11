package com.oopsies.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;

/**
 * ImageUtil is a helper class to process images
 */
public class ImageUtil {

    public static byte[] compressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            Deflater deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            deflater.end();
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Log or rethrow as a runtime exception, as ByteArrayOutputStream.close()
            // should not throw an IOException
            throw new RuntimeException("Failed to compress image", e);
        }
    }

    public static byte[] decompressImage(byte[] data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            Inflater inflater = new Inflater();
            inflater.setInput(data);

            byte[] tmp = new byte[4 * 1024];
            try {
                while (!inflater.finished()) {
                    int count = inflater.inflate(tmp);
                    outputStream.write(tmp, 0, count);
                }
            } catch (DataFormatException e) {
                // Handle or log the exception
                throw new RuntimeException("Failed to decompress image", e);
            } finally {
                inflater.end();
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Log or rethrow as a runtime exception, as ByteArrayOutputStream.close()
            // should not throw an IOException
            throw new RuntimeException("Failed to decompress image", e);
        }
    }
}
