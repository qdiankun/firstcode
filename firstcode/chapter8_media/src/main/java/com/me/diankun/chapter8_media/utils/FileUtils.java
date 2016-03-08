package com.me.diankun.chapter8_media.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by diankun on 2016/3/8.
 */
public class FileUtils {


    /**
     * 将源文件拷贝到目标文件中
     *
     * @param sourceLocation 源文件
     * @param targetLocation 目标文件
     */
    public static void copyFile(File sourceLocation, File targetLocation) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourceLocation);
            out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
