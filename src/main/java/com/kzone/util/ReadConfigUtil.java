package com.kzone.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Jeffy on 2014/5/22 0022.
 */
public class ReadConfigUtil {
    private static Properties pros;
    private String docName;

    public ReadConfigUtil(String docName) {
        this.docName = docName;
    }

    /**
     * getProperty:从文件中读属性. <br/>
     *
     * @author liyf
     * @param proName
     *            属性名
     * @return
     * @since JDK 1.6
     */
    public String getProperty(String proName) {
        String value = "";
        // 读取错误码配置文件

        InputStream ips = null;
        try {
            // 读取错误码配置文件，该文件一定要放在src/main/resources才能读取到；
            ips = ReadConfigUtil.class.getClassLoader().getResourceAsStream(docName);
            pros = new Properties();
            pros.load(ips);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            try {
                if (ips != null) {
                    ips.close();
                }

            } catch (IOException e) {

            }
        }
        if (null != pros.getProperty(proName)) {
            value = pros.getProperty(proName);
        }
        return value;
    }

    public String getProperty(String proName, String defaultValue) {
        String value = getProperty(proName);
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }
        return value;
    }
}
