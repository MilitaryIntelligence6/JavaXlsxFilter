package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.bridge.CoreApiProxy;
import cn.misection.xfilter.ui.constant.ChooseFileType;
import cn.misection.xfilter.ui.dao.ConditionDao;
import cn.misection.xfilter.ui.dao.ConditionDaoImpl;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.service.ConditionService;
import cn.misection.xfilter.ui.service.impl.ConditionServiceImpl;
import cn.misection.xfilter.ui.view.ConditionViewPanel;
import cn.misection.xfilter.ui.view.ControlPanel;

import javax.swing.*;
import java.io.File;

/**
 * @author Administrator
 */
public class ConditionController {
    /**
     * database file
     */
    private final ConditionDao conditionDao = ConditionDaoImpl.getInstance();

    private final ConditionService service = new ConditionServiceImpl();

    private final ControlPanel controlPanel;

    private final ConditionViewPanel conditionViewPanel;

    public ConditionController(ControlPanel controlPanel, ConditionViewPanel conditionViewPanel) {
        this.controlPanel = controlPanel;
        this.conditionViewPanel = conditionViewPanel;
        init();
    }

    private void init() {
        conditionViewPanel.loadAppendData(conditionDao.value().toArray());
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
            conditionDao.addSave(new ConditionEntity(condition));
            // updateUI;
            controlPanel.reset();
            conditionViewPanel.addTail(condition);
        });

        conditionViewPanel.getDelAllButton().addActionListener(
                e -> {
                    conditionDao.clearSave();
                    conditionViewPanel.clearUI();
                }
        );

        controlPanel.getOpenChooseInFileButton().addActionListener(
                e -> controlPanel.chooseAndSave(ChooseFileType.IN)
        );

        controlPanel.getOpenChooseOutFileButton().addActionListener(
                e -> controlPanel.chooseAndSave(ChooseFileType.OUT)
        );

        controlPanel.getExitButton().addActionListener(
                e -> {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "您确定退出系统吗?",
                            "退出系统",
                            JOptionPane.YES_NO_OPTION);
                    switch (choice) {
                        case JOptionPane.YES_OPTION:
                            System.exit(1);
                            break;
                        case JOptionPane.NO_OPTION:
                        default:
                            break;
                    }
                }
        );
        controlPanel.getRunButton().addActionListener(
                e -> {
                    String inPath = NullSafe.safeString(controlPanel.getInFilePathField().getText());
                    String outPath = NullSafe.safeString(controlPanel.getOutFilePathField().getText());
                    if (inPath.isEmpty() || outPath.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "输入或者输出文件的路径不能为空",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    File inFile = new File(inPath);
                    File outFile = new File(outPath);
                    if (!inFile.exists()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "输入文件不存在, 请检查路径!",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (outFile.exists()) {
                        int override = JOptionPane.showConfirmDialog(
                                null,
                                "输出文件已存在, 确认覆盖?",
                                "WARNING",
                                JOptionPane.YES_NO_OPTION
                        );
                        switch (override) {
                            case JOptionPane.YES_OPTION:
                                break;
                            case JOptionPane.NO_OPTION:
                                return;
                            default:
                                return;
                        }
                    }
                    CoreApiProxy apiProxy = new CoreApiProxy.Builder()
                            .putInPath(inPath)
                            .putOutPath(outPath)
                            .putConditionList(conditionDao.value())
                            .build();
                    apiProxy.callFilter();
                }
        );
    }
}
