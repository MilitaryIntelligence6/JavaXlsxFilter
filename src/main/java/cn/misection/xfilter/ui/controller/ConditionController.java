package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.view.ControlPanel;
import cn.misection.xfilter.ui.view.DetailsPanel;
import cn.misection.xfilter.ui.dao.DataSource;

import javax.swing.*;

/**
 * @author Administrator
 */
public class ConditionController {
    /**
     * database file
     */
    private final DataSource dataSource = DataSource.getInstance();

    private final ControlPanel conditionForm;

    private final DetailsPanel detailsPanel;

    public ConditionController(ControlPanel conditionForm, DetailsPanel detailsPanel) {
        this.conditionForm = conditionForm;
        this.detailsPanel = detailsPanel;
        init();
    }

    private void init() {
        this.detailsPanel.loadData(this.dataSource.loadOut());
        // submit user
        this.conditionForm.submitUsers(e -> {
            String condition = this.conditionForm.condition();
            // simple validations
            if(condition.isEmpty()) {
                JOptionPane.showMessageDialog(this.conditionForm, "条件不能为空!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // update data;
            this.dataSource.addAndSave(new ConditionEntity(condition));
            // updateUI;
            this.conditionForm.reset();
            this.detailsPanel.addTail(condition);
        });
    }
}
