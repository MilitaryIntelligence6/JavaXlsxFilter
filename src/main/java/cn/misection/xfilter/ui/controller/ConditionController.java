package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.ui.dao.DataSource;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.view.ConditionViewPanel;
import cn.misection.xfilter.ui.view.ControlPanel;

import javax.swing.*;

/**
 * @author Administrator
 */
public class ConditionController {
    /**
     * database file
     */
    private final DataSource dataSource = DataSource.getInstance();

    private final ControlPanel controlPanel;

    private final ConditionViewPanel conditionViewPanel;

    public ConditionController(ControlPanel controlPanel, ConditionViewPanel conditionViewPanel) {
        this.controlPanel = controlPanel;
        this.conditionViewPanel = conditionViewPanel;
        init();
    }

    private void init() {
        conditionViewPanel.loadAppendData(dataSource.loadOutOnlyOnceFromDb());
        registerActionListener();
    }

    private void registerActionListener() {
        controlPanel.getAddConditionButton().addActionListener(e -> {
            String condition = this.controlPanel.condition();
            if (condition.isEmpty()) {
                JOptionPane.showMessageDialog(this.controlPanel, "条件不能为空!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // update data;
            dataSource.addAndSave(new ConditionEntity(condition));
            // updateUI;
            controlPanel.reset();
            conditionViewPanel.addTail(condition);
        });

        conditionViewPanel.getDelAllButton().addActionListener(
                e -> {
                    dataSource.clearData();
                    conditionViewPanel.clearUI();
                }
        );
    }
}
