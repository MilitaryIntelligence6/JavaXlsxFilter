package cn.misection.xfilter.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Administrator
 */
public class DetailsPanel extends JPanel {

    /**
     * Table for user data;
     */
    private final JTable userTable = new JTable();

    /**
     * table column;
     */
    private final String[] userTableColumn = {"已有筛选条件"};

    /**
     * back button;
     */
    private final JButton backButton = new JButton("刪除选中");

    private final JToolBar toolBar = new JToolBar();

    private final JScrollPane userTableScroll = new JScrollPane(userTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private final DefaultTableModel defaultTableModel = (DefaultTableModel) userTable.getModel();

    public DetailsPanel() {
        // uses box layout
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // scroll bar for table
        this.add(toolBar);
        toolBar.add(backButton);
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        this.add(userTableScroll);
    }

    /**
     * gets users from database and loads to table
     *
     * @param showElements
     */
    public void loadData(Object[] showElements) {
        defaultTableModel.setColumnIdentifiers(userTableColumn);
        for (Object ele : showElements) {
            Object[] row = new String[]{String.valueOf(ele).trim()};
            defaultTableModel.addRow(row);
        }
    }

    public void addTail(String value) {
        defaultTableModel.addRow(new String[]{value});
    }

    public void reload() {

    }

    /**
     * event listener for back button;
     *
     * @param actionListener
     */
    public void delButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }
}
