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

    private final DetailsPanel detailsPanel = new DetailsPanel();

    private final ConditionController conditionController = new ConditionController(controlPanel, detailsPanel);

    private final JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);

    private final DarkModToggleButton switchSkinButton = new DarkModToggleButton();

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
        // sets our layout as a card layout
        this.setLayout(new BorderLayout());
        // initialize user controller
        // icon for our application
        this.setIconImage(new ImageIcon("src/assets/appicon.png").getImage());
        // size of our application frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initSubViewAndLayout() {
        // adds view to card layout with unique constraints
        switchSkinButton.setPreferredSize(new Dimension(50, 20));
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        toolBar.add(switchSkinButton);
        this.add(toolBar, BorderLayout.NORTH);

        this.add(controlPanel, BorderLayout.WEST);
        this.add(detailsPanel, BorderLayout.CENTER);
    }

    private void initBounds() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        CenterUtil.keepCenter(this);
    }
}
