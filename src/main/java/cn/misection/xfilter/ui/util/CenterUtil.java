package cn.misection.xfilter.ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName CenterUtil
 * @Description TODO
 * @CreateTime 2021年06月12日 00:25:00
 */
public class CenterUtil {

    /**
     * 得到显示器屏幕的宽高;
     */
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    private CenterUtil() {
        throw new RuntimeException("here is no CenterUtil instance for you");
    }

    public static void keepCenter(JFrame frame) {
        frame.setLocation(
                (SCREEN_WIDTH - frame.getWidth()) / 2,
                (SCREEN_HEIGHT - frame.getHeight()) / 2);
    }
}
