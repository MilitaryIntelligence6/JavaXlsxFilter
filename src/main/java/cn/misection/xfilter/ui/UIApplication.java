package cn.misection.xfilter.ui;

import cn.misection.xfilter.ui.view.MainFrame;

import javax.swing.*;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName UIApplication
 * @Description TODO
 * @CreateTime 2021年06月13日 23:06:00
 */
public class UIApplication {
    public static void launch() {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
