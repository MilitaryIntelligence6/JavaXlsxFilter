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
                    if (inPath.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "输入文件路径不能为空",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (!(inPath.endsWith(".xlsx") || inPath.endsWith(".xls"))) {
                        JOptionPane.showMessageDialog(
                                null,
                                "输入文件必须以.xlsx或者.xls结尾(虽然实际上没啥用)",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (outPath.isEmpty()) {
                        outPath = String.format("%s.out.xlsx", inPath);
                        int newPath = JOptionPane.showConfirmDialog(
                                null,
                                String.format("输出文件路径为空, 将使用%s作为输出路径, 我懒得再做判断了, 所以后缀比较长, 可以自行删除", outPath),
                                "ERROR",
                                JOptionPane.YES_NO_OPTION
                        );
                        switch (newPath) {
                            case JOptionPane.YES_OPTION:
                                break;
                            case JOptionPane.NO_OPTION:
                                return;
                            default:
                                return;
                        }
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
                    JOptionPane.showMessageDialog(
                            null,
                            String.format("不出意外的话, 已经成功输出到%s", outPath),
                            "恭喜你输出成功!",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
        );
    }
}
