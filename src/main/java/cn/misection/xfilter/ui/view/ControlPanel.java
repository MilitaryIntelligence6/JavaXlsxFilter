package cn.misection.xfilter.ui.view;

import cn.misection.xfilter.common.constant.StringPool;

import javax.swing.*;
import java.awt.*;

/**
 * @author Administrator
 */
public class ControlPanel extends JPanel {

    private final JPanel fileChooserPanel = new JPanel();

    private final JLabel fileChoosePrompt = new JLabel("请输入文件路径或者从下方按钮选择文件");

    private final JPanel pathFieldPanel = new JPanel();

    private final JTextField filePathField = new JTextField();

    private final JButton openChooseButton = new JButton("选择文件");

    private final JFileChooser fileChooser = new JFileChooser();

    private final JPanel buttonPanel = new JPanel();

    private final JLabel conditionLabel = new JLabel("输入筛选条件: ");

    private final JTextField conditionField = new JTextField();

    private final JButton addConditionButton = new JButton("增加筛选过滤条件");

    private final JButton viewButton = new JButton("View All Users");

    private final JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);

    private final JLabel darkLabel = new JLabel("夜间模式");

    private final DarkModToggleButton switchSkinButton = new DarkModToggleButton();

    public ControlPanel() {
        init();
    }

    private void init() {
        initLayout();
        initToolBar();
        initFileChoosePanel();
        initButtonPanel();
    }


    private void initLayout() {
        this.setLayout(new GridLayout(2, 1, 10, 10));
//        this.setLayout(new BorderLayout());
        this.add(fileChooserPanel);
        this.add(buttonPanel);
//        // space between fields
//        Insets fieldsInset = new Insets(0, 0, 10, 0);
//        // space between buttons
//        Insets buttonInset = new Insets(20, 0, 0, 0);
//        // uses Grid Bag Layout
//        setLayout(new GridBagLayout());
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//
//        this.add(fileChooser);

//        gridBagConstraints.insets = fieldsInset;
//        gridBagConstraints.fill = GridBagConstraints.NONE;
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.anchor = GridBagConstraints.WEST;
//
//        this.add(conditionLabel, gridBagConstraints);
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 1;
//
//        conditionField.setPreferredSize(new Dimension(200, 40));
//        this.add(conditionField, gridBagConstraints);
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 2;
//        gridBagConstraints.anchor = GridBagConstraints.WEST;
//
////        add(lastnameLabel, gridBagConstraints);
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 3;
//
////        add(lastNameField, gridBagConstraints);
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 4;
//        gridBagConstraints.insets = buttonInset;
//
//        add(addConditionButton, gridBagConstraints);
//
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 5;
//        gridBagConstraints.insets = buttonInset;
//
//        add(viewButton, gridBagConstraints);
    }

    private void initFileChoosePanel() {
//        fileChooserPanel.add(fileChooser);
//        fileChooser.getSelectedFile();
        fileChooserPanel.setLayout(new GridLayout(4, 1, 10, 10));
        fileChooserPanel.add(toolBar);
        fileChooserPanel.add(pathFieldPanel);
        filePathField.setPreferredSize(new Dimension(200, 30));
        pathFieldPanel.add(filePathField);
        pathFieldPanel.add(openChooseButton);

        openChooseButton.addActionListener(
                e -> {
                    fileChooser.showOpenDialog(null);
                    filePathField.setText(String.valueOf(fileChooser.getSelectedFile()));
                }
        );

    }

    private void initButtonPanel() {
        addConditionButton.setPreferredSize(new Dimension(278, 40));
        viewButton.setPreferredSize(new Dimension(278, 40));

        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(conditionLabel);
        buttonPanel.add(conditionField);
        buttonPanel.add(addConditionButton);
        buttonPanel.add(viewButton);
    }

    private void initToolBar() {
        toolBar.setLayout(new FlowLayout());
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        darkLabel.setPreferredSize(new Dimension(50, 20));
        switchSkinButton.setPreferredSize(new Dimension(50, 20));
        toolBar.add(darkLabel);
        toolBar.add(switchSkinButton);
    }

    /**
     * reset fields;
     */
    public void reset() {
        conditionField.setText(StringPool.EMPTY.value());
    }

    public String condition() {
        return String.valueOf(conditionField.getText()).trim();
    }

    public JButton getAddConditionButton() {
        return addConditionButton;
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
