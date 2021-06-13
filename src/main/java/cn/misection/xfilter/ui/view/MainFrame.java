package cn.misection.xfilter.ui.view;


import cn.misection.xfilter.ui.controller.ConditionController;
import cn.misection.xfilter.ui.util.CenterUtil;
import cn.misection.xfilter.ui.util.SkinManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Administrator
 */
public class MainFrame extends JFrame {

    /**
     * 先设皮肤, 再加载;
     */
    static {
        SkinManager.chooseSetSkinMod();
    }
    private final ControlPanel controlPanel = new ControlPanel();

    private final ConditionViewPanel conditionViewPanel = new ConditionViewPanel();

    private final ConditionController conditionController = new ConditionController(controlPanel, conditionViewPanel);

    private static final int FRAME_WIDTH = 600;

    private static final int FRAME_HEIGHT = 400;

    public MainFrame() {
        super("Java Xlsx 筛选器 军情六处工作室出品");
        init();
    }

    private void init() {
        initBaseUI();
        initSubViewAndLayout();
        initBounds();
        this.setVisible(true);
    }

    private void initBaseUI() {
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("src/assets/appicon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initSubViewAndLayout() {
        this.add(controlPanel, BorderLayout.WEST);
        this.add(conditionViewPanel, BorderLayout.CENTER);
    }

    private void initBounds() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        CenterUtil.keepCenter(this);
    }
}
