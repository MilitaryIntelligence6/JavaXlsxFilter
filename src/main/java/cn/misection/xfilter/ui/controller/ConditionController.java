package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.bridge.CoreApiProxy;
import cn.misection.xfilter.ui.constant.ChooseFileType;
import cn.misection.xfilter.ui.constant.StringPool;
import cn.misection.xfilter.ui.dao.ConditionDao;
import cn.misection.xfilter.ui.dao.impl.ConditionDaoImpl;
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
        registerListeners();
    }

    private void registerListeners() {
        registerAddConditionListener();
        registerDelSingleListener();
        registerDelAllListener();
        registerOpenInFileListener();
        registerOpenOutFileListener();
        registerExitListener();
        registerRunListener();
    }

    private void registerOpenInFileListener() {
        controlPanel.getOpenChooseInFileButton().addActionListener(
                e -> controlPanel.chooseAndSave(ChooseFileType.IN)
        );
    }

    private void registerOpenOutFileListener() {
        controlPanel.getOpenChooseOutFileButton().addActionListener(
                e -> controlPanel.chooseAndSave(ChooseFileType.OUT)
        );
    }

    private void registerExitListener() {
        controlPanel.getExitButton().addActionListener(
                e -> {
                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "你舍得退出系统吗?",
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
    }

    private void registerAddConditionListener() {
        controlPanel.getAddConditionButton().addActionListener(e -> {
            String condition = this.controlPanel.condition();
            if (condition.isEmpty()) {
                JOptionPane.showMessageDialog(this.controlPanel, "条件不能为空!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // update data;
            conditionDao.saveAddChange(new ConditionEntity(condition));
            // updateUI;
            controlPanel.reset();
            conditionViewPanel.addTail(condition);
        });
    }

    private void registerDelSingleListener() {
        conditionViewPanel.getDelSelectedButton().addActionListener(
                e -> {
                    int selectedRow = conditionViewPanel.getDataTable().getSelectedRow();
                    if (selectedRow != -1) {
                        conditionViewPanel.delSelected(selectedRow);
                        conditionDao.saveRemoveChange(selectedRow);
                    }
                }
        );
    }

    private void registerDelAllListener() {
        conditionViewPanel.getDelAllButton().addActionListener(
                e -> {
                    conditionDao.saveClearChange();
                    conditionViewPanel.clearUI();
                }
        );
    }

    private void registerRunListener() {
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
                    if (!(inPath.endsWith(StringPool.SUFFIX_XLSX.value())
                            || inPath.endsWith(StringPool.SUFFIX_XLS.value()))) {
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
                    if (outFile.exists() && outFile.isFile()) {
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
                    if (outFile.exists() && outFile.isDirectory()) {
                        outFile = new File(String.format("%s\\%s.out.xlsx", outFile, inFile.getName()));
                        outPath = outFile.getAbsolutePath();
                        int newPath = JOptionPane.showConfirmDialog(
                                null,
                                String.format("你选择的输出文件的是一个目录, 那么我输出到 %s 哦?", outFile.getAbsolutePath()),
                                "WARNING",
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
                    if (!outFile.exists()) {
                        // 只创建父目录, 其他管不了;
                        File parentDir = new File(outFile.getParent());
                        if (!parentDir.exists()) {
                            if (!parentDir.mkdirs()) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "创建目录失败, 请选择正确的路径!",
                                        "ERROR",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
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
