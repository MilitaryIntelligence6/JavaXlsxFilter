package cn.misection.xfilter.ui.view;


import cn.misection.xfilter.ui.controller.ConditionController;
import cn.misection.xfilter.ui.util.CenterUtil;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    /**
     * Card layout for switching view;
     */
    private CardLayout cardLayout;

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    public MainFrame() {
        super("Java Swing MVC");
        cardLayout = new CardLayout();
        ConditionForm conditionForm = new ConditionForm();
        UserDetailsPanel userDetailsPanel = new UserDetailsPanel();
        // sets our layout as a card layout
        setLayout(cardLayout);

        // initialize user controller
        new ConditionController(conditionForm, userDetailsPanel);

        // adds view to card layout with unique constraints
        add(conditionForm, "form");
        add(userDetailsPanel, "user details");
        // switch view according to its constraints on click
        conditionForm.viewUsers(e -> cardLayout.show(MainFrame.this.getContentPane(), "user details"));
        userDetailsPanel.backButton(e -> cardLayout.show(MainFrame.this.getContentPane(), "form"));

        // icon for our application
        ImageIcon imageIcon = new ImageIcon("src/assets/appicon.png");
        setIconImage(imageIcon.getImage());

        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CenterUtil.keepCenter(this);
        setVisible(true);
    }
}
