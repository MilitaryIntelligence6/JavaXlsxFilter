package cn.misection.xfilter.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

/**
 * @author Administrator
 */
public class ConditionViewPanel extends JPanel {

    /**
     * table column 列名;
     */
    private final String[] userTableColumn = {"已有筛选条件"};

    /**
     * Table for user data;
     */
    private final JTable userTable = new JTable();

    private final JToolBar toolBar = new JToolBar();

    private final JScrollPane userTableScroll = new JScrollPane(userTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private final DefaultTableModel defaultTableModel = (DefaultTableModel) userTable.getModel();

    /**
     * back button;
     */
    private final JButton delSelectedButton = new JButton("  刪除选中  ");

    private final JButton delAllButton = new JButton("  清空所有条件  ");

    public ConditionViewPanel() {
        init();
        initToolbar();
    }

    private void init() {
        // uses box layout
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // scroll bar for table
        this.add(toolBar);
        this.add(userTableScroll);
    }

    private void initToolbar() {
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        toolBar.add(delSelectedButton);
        toolBar.add(delAllButton);
    }

    /**
     * gets users from database and loads to table
     *
     * @param showElements
     */
    public void loadAppendData(Object[] showElements) {
        defaultTableModel.setColumnIdentifiers(userTableColumn);
        for (Object ele : showElements) {
            Object[] row = new String[]{String.valueOf(ele).trim()};
            defaultTableModel.addRow(row);
        }
    }

    public void addTail(String value) {
        defaultTableModel.addRow(new String[]{value});
    }

    public void clearUI() {
        defaultTableModel.setDataVector(new Object[0][0], userTableColumn);
    }

    public void reload() {

    }

    public JButton getDelSelectedButton() {
        return delSelectedButton;
    }

    public JButton getDelAllButton() {
        return delAllButton;
    }
}
