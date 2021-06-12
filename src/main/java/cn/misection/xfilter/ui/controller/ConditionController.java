package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.config.ResourceBundle;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.view.ConditionForm;
import cn.misection.xfilter.ui.view.DetailsPanel;
import cn.misection.xfilter.ui.dao.Database;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Administrator
 */
public class ConditionController {
    /**
     * database file
     */
    private static final File dbFile = requireDbFile();
    private Database database;
    private ConditionForm conditionForm;
    private DetailsPanel detailsPanel;

    public ConditionController(ConditionForm conditionForm, DetailsPanel detailsPanel) {
        this.database = new Database();
        this.conditionForm = conditionForm;
        this.detailsPanel = detailsPanel;

        // submit user
        this.conditionForm.submitUsers(e -> {
            String condition = this.conditionForm.condition();
            // simple validations
            if(condition.isEmpty()) {
                JOptionPane.showMessageDialog(this.conditionForm, "条件不能为空!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.database.addCondition(new ConditionEntity(condition));
            this.database.saveCondition(dbFile);
            this.conditionForm.reset();
        });

        // load users
        this.conditionForm.viewUsers(e -> {
            this.detailsPanel.getUsers(this.database.loadUsers(dbFile));
        });
    }

    private static File requireDbFile() {
        URL resource = ConditionController.class.getResource(ResourceBundle.CONDITION_DB.getPath());
        if (resource != null) {
            String path = resource.getPath();
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
        return new File(ResourceBundle.CONDITION_DB.getPath());
    }
}
