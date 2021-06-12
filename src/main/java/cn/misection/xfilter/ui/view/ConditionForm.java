package cn.misection.xfilter.ui.view;

import cn.misection.xfilter.common.constant.StringPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConditionForm extends JPanel {

    private final JTextField conditionField;

    private final JButton addConditionButton;
    private final JButton viewButton;

    public ConditionForm() {

        JLabel conditionLabel = new JLabel("输入筛选条件: ");
//        JLabel lastnameLabel = new JLabel("Last Name: ");

        conditionField = new JTextField(25);
//        lastNameField = new JTextField(25);

        addConditionButton = new JButton("增加筛选过滤条件");
        addConditionButton.setPreferredSize(new Dimension(278, 40));
        viewButton = new JButton("View All Users");
        viewButton.setPreferredSize(new Dimension(278, 40));
        // space between fields
        Insets fieldsInset = new Insets(0, 0, 10, 0);
        // space between buttons
        Insets buttonInset = new Insets(20, 0, 0, 0);

        // uses Grid Bag Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = fieldsInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

        add(conditionLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        add(conditionField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;

//        add(lastnameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;

//        add(lastNameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = buttonInset;

        add(addConditionButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = buttonInset;

        add(viewButton, gridBagConstraints);
    }

    /**
     * reset fields;
     */
    public void reset() {
        conditionField.setText(StringPool.EMPTY.value());
    }

    public String getFirstname() {
        return conditionField.getText();
    }

    public void submitUsers(ActionListener actionListener) {
        addConditionButton.addActionListener(actionListener);
    }

    public void viewUsers(ActionListener actionListener) {
        viewButton.addActionListener(actionListener);
    }
}
