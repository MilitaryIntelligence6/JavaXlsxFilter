package cn.misection.xfilter.ui.controller;

import cn.misection.xfilter.common.constant.StringPool;
import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.bridge.CoreApiProxy;
import cn.misection.xfilter.ui.config.PropertiesBundle;
import cn.misection.xfilter.ui.constant.ChooseFileType;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.service.ConditionService;
import cn.misection.xfilter.ui.service.impl.ConditionServiceImpl;
import cn.misection.xfilter.ui.util.DialogPopper;
import cn.misection.xfilter.ui.util.PropertiesProxy;
import cn.misection.xfilter.ui.view.ConditionViewPanel;
import cn.misection.xfilter.ui.view.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 */
public class ConditionController {

    private final ConditionService service = ConditionServiceImpl.getInstance();

    private final ControlPanel controlPanel;

    private final ConditionViewPanel conditionViewPanel;

    public ConditionController(ControlPanel controlPanel, ConditionViewPanel conditionViewPanel) {
        this.controlPanel = controlPanel;
        this.conditionViewPanel = conditionViewPanel;
        init();
    }

    private void init() {
        conditionViewPanel.loadAppendData(service.value().toArray());
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
                            "?????????????????????????",
                            "????????????",
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
                JOptionPane.showMessageDialog(this.controlPanel, "??????????????????!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            // update data;
            service.add(new ConditionEntity(condition));
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
                        service.remove(selectedRow);     
                    }
                }
        );
    }

    private void registerDelAllListener() {
        conditionViewPanel.getDelAllButton().addActionListener(
                e -> {
                    service.clear();
                    conditionViewPanel.clearUI();
                }
        );
    }

    private void registerRunListener() {
        controlPanel.getGoRunButton().addActionListener(
                e -> {
                    String inPath = NullSafe.safeString(controlPanel.getInFilePathField().getText());
                    String outPath = NullSafe.safeString(controlPanel.getOutFilePathField().getText());
                    if (inPath.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "??????????????????????????????",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    if (!(inPath.endsWith(StringPool.SUFFIX_XLSX.value())
                            || inPath.endsWith(StringPool.SUFFIX_XLS.value()))) {
                        DialogPopper.error("?????????????????????.xlsx??????.xls??????(????????????????????????)");
                        return;
                    }
                    if (outPath.isEmpty()) {
                        outPath = String.format("%s.out.xlsx", inPath);
                        int newPath = DialogPopper.confirm(String.format("????????????????????????, ?????????%s??????????????????, " +
                                "????????????????????????, ?????????????????????, ??????????????????", outPath));
                        switch (newPath) {
                            case JOptionPane.YES_OPTION:
                                break;
                            case JOptionPane.NO_OPTION:
                            default:
                                return;
                        }
                    }
                    // path string ??????, ???????????????????????????;
                    PropertiesProxy.putAndSave(
                            PropertiesBundle.LAST_IN_FILE.getLiteral(),
                            inPath);
                    PropertiesProxy.putAndSave(
                            PropertiesBundle.LAST_OUT_FILE.getLiteral(),
                            outPath);
                    File inFile = new File(inPath);
                    File outFile = new File(outPath);
                    if (!inFile.exists()) {
                        DialogPopper.error("?????????????????????, ???????????????!");
                        return;
                    }
                    if (inFile.isDirectory()) {
                        DialogPopper.error("???????????????????????????, ???????????????????????????!");
                        return;
                    }
                    if (outFile.exists() && outFile.isFile()) {
                        int override = DialogPopper.confirm("?????????????????????, ?????????????");
                        switch (override) {
                            case JOptionPane.YES_OPTION:
                                break;
                            case JOptionPane.NO_OPTION:
                            default:
                                return;
                        }
                    }
                    if (outFile.exists() && outFile.isDirectory()) {
                        outFile = new File(String.format("%s\\%s.out.xlsx", outFile, inFile.getName()));
                        outPath = outFile.getAbsolutePath();
                        int newPath = DialogPopper.confirm(String.format("??????????????????????????????????????????, ????????????????????? %s ???????",
                                outFile.getAbsolutePath()));
                        switch (newPath) {
                            case JOptionPane.YES_OPTION:
                                break;
                            case JOptionPane.NO_OPTION:
                            default:
                                return;
                        }
                    }
                    if (!outFile.exists()) {
                        // ??????????????????, ???????????????;
                        if (outFile.getParent() == null) {
                            DialogPopper.error("?????????????????????, ?????????????????????????????????!");
                            return;
                        }
                        File parentDir = new File(outFile.getParent());
                        if (!parentDir.exists()) {
                            if (!parentDir.mkdirs()) {
                                DialogPopper.error("??????????????????, ???????????????????????????????????????????????????????????????!");
                                return;
                            }
                        }
                    }
                    CoreApiProxy apiProxy = new CoreApiProxy.Builder()
                            .putInPath(inPath)
                            .putOutPath(outPath)
                            .putConditionList(service.value())
                            .build();
                    if (!apiProxy.callFilter()) {
                        return;
                    }
                    int choice = DialogPopper.confirm("?????????????????????!",
                            String.format("??????????????????, ?????????????????????%s\n ???????????????????????????????", outPath));
                    switch (choice) {
                        case JOptionPane.YES_OPTION:
                            try {
                                Desktop.getDesktop().open(outFile.getParentFile());
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                            break;
                        case JOptionPane.NO_OPTION:
                        default:
                            break;
                    }
                }
        );
    }
}
