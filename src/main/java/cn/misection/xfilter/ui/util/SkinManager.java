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
public class SkinManager {

    private static SkinStatus status = SkinStatus.INTELLIJ;

    private static boolean beenDark = false;

    private SkinManager() {
        throw new RuntimeException("here are no skinProxy instance for you");
    }

    public static void changeSkin() {
        if (beenDark) {
            setIntellijSkin();
        } else {
            setDarculaSkin();
        }
    }

    public static void setDarculaSkin() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            status = SkinStatus.DARCULA;
            beenDark = true;
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void setIntellijSkin() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            status = SkinStatus.INTELLIJ;
            beenDark = false;
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}

enum SkinStatus {
    /**
     * 皮肤状态;
     */
    INTELLIJ,

    DARCULA,
}
