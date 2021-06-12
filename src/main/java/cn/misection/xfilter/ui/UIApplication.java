package cn.misection.xfilter.ui;

import cn.misection.xfilter.ui.util.SkinManager;
import cn.misection.xfilter.ui.view.MainFrame;

import javax.swing.*;

/**
 * @author Administrator
 */
public class UIApplication {
    public static void main(String[] args) {
        // runs in AWT thread
//        SkinUtil.setDarculaSkin();
        SkinManager.setIntellijSkin();
        var hello = new Object();
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
