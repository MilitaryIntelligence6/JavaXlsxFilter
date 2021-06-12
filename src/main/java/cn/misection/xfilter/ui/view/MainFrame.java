package cn.misection.xfilter.ui.view;


import cn.misection.xfilter.ui.controller.ConditionController;
import cn.misection.xfilter.ui.util.CenterUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Administrator
 */
public class MainFrame extends JFrame {

    /**
     * Card layout for switching view;
     */
    private final CardLayout cardLayout = new CardLayout();

    private final ConditionForm conditionForm = new ConditionForm();

    private final DetailsPanel detailsPanel = new DetailsPanel();

    private final ConditionController conditionController = new ConditionController(conditionForm, detailsPanel);

    private

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
        this.add(conditionForm, BorderLayout.WEST);
        this.add(detailsPanel, BorderLayout.CENTER);
        // switch view according to its constraints on click
//        conditionForm.viewUsers(e -> cardLayout.show(MainFrame.this.getContentPane(), "user details"));
//        detailsPanel.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "form"));
    }

    private void initBounds() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        CenterUtil.keepCenter(this);
    }
}
