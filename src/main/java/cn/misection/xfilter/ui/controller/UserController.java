package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.ui.entity.User;
import cn.misection.xfilter.ui.view.ConditionForm;
import cn.misection.xfilter.ui.view.UserDetailsPanel;
import cn.misection.xfilter.ui.entity.Database;

import javax.swing.*;
import java.io.File;

/**
 * @author Administrator
 */
public class UserController {
    /**
     * database file
     */
    private String databaseFile = "src\\data\\database.txt";
    private Database database;
    private ConditionForm conditionForm;
    private UserDetailsPanel userDetailsPanel;

    public UserController(ConditionForm conditionForm, UserDetailsPanel userDetailsPanel) {
        this.database = new Database();
        this.conditionForm = conditionForm;
        this.userDetailsPanel = userDetailsPanel;

        // submit user
        this.conditionForm.submitUsers(e -> {
            String firstname = this.conditionForm.getFirstname().trim();
            String lastname = this.conditionForm.getLastname().trim();

            // simple validations
            if(firstname.isEmpty()) {
                JOptionPane.showMessageDialog(this.conditionForm, "First Name Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else if(lastname.isEmpty()) {
                JOptionPane.showMessageDialog(this.conditionForm, "Last Name Required.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.database.addUser(new User(firstname, lastname));
            this.database.saveUser(new File(databaseFile));
            this.conditionForm.reset(true);
        });

        // load users
        this.conditionForm.viewUsers(e -> {
            this.userDetailsPanel.getUsers(this.database.loadUsers(new File(databaseFile)));
        });
    }
}
