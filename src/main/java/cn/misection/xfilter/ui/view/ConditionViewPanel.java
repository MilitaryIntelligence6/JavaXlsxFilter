package cn.misection.xfilter.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private final JTable dataTable = new JTable();

    private final JToolBar toolBar = new JToolBar();

    private final JScrollPane userTableScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private final DefaultTableModel defaultTableModel = (DefaultTableModel) dataTable.getModel();

    /**
     * back button;
     */
    private final JButton delSelectedButton = new JButton("  刪除选中  ");

    private final JButton delAllButton = new JButton("  清空所有条件  ");

    public ConditionViewPanel() {
        init();
    }

    private void init() {
        // uses box layout
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        // scroll bar for table
        this.add(toolBar);
        this.add(userTableScroll);
        defaultTableModel.setColumnIdentifiers(userTableColumn);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initToolbar();
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
        for (Object ele : showElements) {
            Object[] row = new String[]{String.valueOf(ele).trim()};
            defaultTableModel.addRow(row);
        }
    }

    public void addTail(String value) {
        defaultTableModel.addRow(new String[]{value});
    }

    public void delSelected(int selectedRow) {
        defaultTableModel.removeRow(selectedRow);
    }

    public void clearUI() {
        defaultTableModel.setDataVector(new Object[0][0], userTableColumn);
    }

    public JTable getDataTable() {
        return dataTable;
    }

    public JButton getDelSelectedButton() {
        return delSelectedButton;
    }

    public JButton getDelAllButton() {
        return delAllButton;
    }
}
