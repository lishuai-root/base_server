package com.shuai.base.utils.fileUtils;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 17:34
 * @version: 1.0
 */

@Log4j2
public class FileUtils {

    public static final Map<String, String> CONTENT_TYPE;
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";

    public static final int BUFFERED_SIZE = 2048;

    static {
        CONTENT_TYPE = new HashMap<>();
        CONTENT_TYPE.put(".html", "text/html");
        CONTENT_TYPE.put(".txt", "text/plain");
        CONTENT_TYPE.put(".xml", "text/xml");
        CONTENT_TYPE.put(".gif", "image/gif");
        CONTENT_TYPE.put(".jpeg", "image/jpeg");
        CONTENT_TYPE.put(".png", "image/png");
        CONTENT_TYPE.put("json", "application/json");
        CONTENT_TYPE.put("data", "multipart/form-data");
    }


    public static String getContentTypeByUri(String uri) {
        int index = uri.lastIndexOf(".");
        return getContentType(uri.substring(index));
    }

    public static String getContentType(String suffix) {
        return StringUtils.hasText(suffix) ? CONTENT_TYPE.get(suffix) : DEFAULT_CONTENT_TYPE;
    }

    public static boolean cpFile(File oldFile, File newFile) {
        OutputStream out = null;
        boolean b;
        try {
            out = new FileOutputStream(newFile);
            b = writeFile(out, oldFile);
        } catch (FileNotFoundException e) {
            b = false;
            log.error(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public static boolean writeFile(OutputStream out, File file) {
        if (!file.isFile() || !file.exists()) {
            return false;
        }

        boolean b;
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            b = writeFile(out, input);
        } catch (FileNotFoundException e) {
            b = false;
            log.error(e.getMessage());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return b;
    }

    public static boolean writeFile(OutputStream out, InputStream input) {
        byte[] buffer = new byte[BUFFERED_SIZE << 2];
        int index;
        boolean b = true;

        try {
            while ((index = input.read(buffer)) != -1) {
                out.write(buffer, 0, index);
            }
            out.flush();
        } catch (IOException e) {
            b = false;
            log.error(e.getMessage());
        }
        return b;
    }

    public static File getStaticSource(String uri) {
        return getFile("classpath:", uri);
    }

    public static File getFile(String dir, String fileName) {
        return new File(dir + File.separatorChar + fileName);
    }

    private static int write(OutputStream out, byte[] buffer, byte[] bytes, int index) {
        return 0;
    }
}
