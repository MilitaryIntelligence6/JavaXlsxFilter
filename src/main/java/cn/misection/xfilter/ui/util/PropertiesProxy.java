package cn.misection.xfilter.ui.util;

import cn.misection.xfilter.ui.config.BuildConfig;
import cn.misection.xfilter.ui.config.ResourceBundle;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName PropertiesUtil
 * @Description TODO
 * @CreateTime 2021年06月12日 20:39:00
 */
public class PropertiesProxy {

    private static final Properties properties = new Properties();

    static {
        File config = new File(ResourceBundle.CONFIG.getPath());
        try {
            properties.load(new FileReader(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void putAndSave(String key, String value) {
        properties.setProperty(key, value);
        try {
            File file = new File(ResourceBundle.CONFIG.getPath());
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "TODO auto log");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
