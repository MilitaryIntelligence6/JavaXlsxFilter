package cn.misection.xfilter.ui.util;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName SkinUtil
 * @Description TODO
 * @CreateTime 2021年06月12日 00:13:00
 */
public class SkinProxy {

    private SkinProxy() {
        throw new RuntimeException("here are no skinProxy instance for you");
    }

    public static void setDarculaSkin() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void setIntellijSkin() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
