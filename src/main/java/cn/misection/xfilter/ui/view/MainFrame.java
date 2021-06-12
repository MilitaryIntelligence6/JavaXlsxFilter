package cn.misection.xfilter.ui.view;


import cn.misection.xfilter.ui.controller.ConditionController;
import cn.misection.xfilter.ui.util.CenterUtil;
import cn.misection.xfilter.ui.util.SkinManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * @author Administrator
 */
public class MainFrame extends JFrame {

    /**
     * Card layout for switching view;
     */
    private final CardLayout cardLayout = new CardLayout();

    private final ControlPanel controlPanel = new ControlPanel();

    private final DetailsPanel detailsPanel = new DetailsPanel();

    private final ConditionController conditionController = new ConditionController(controlPanel, detailsPanel);

    private final JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);

    private final JsToggleButton switchSkinButton = new JsToggleButton();

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
        switchSkinButton.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        JOptionPane.showMessageDialog(null, "shit");
                        SkinManager.changeSkin();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                    }

                    @Override
                    public void mouseWheelMoved(MouseWheelEvent e) {
                        super.mouseWheelMoved(e);
                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        super.mouseDragged(e);
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        super.mouseMoved(e);
                    }
                }
        );
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        toolBar.add(switchSkinButton);
        this.add(toolBar, BorderLayout.NORTH);


//        toolBar.add()
        this.add(controlPanel, BorderLayout.WEST);
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
