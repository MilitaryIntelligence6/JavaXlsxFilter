package cn.misection.xfilter.ui.view;

import cn.misection.xfilter.common.constant.StringPool;
import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.config.PropertiesBundle;
import cn.misection.xfilter.ui.constant.ChooseFileType;
import cn.misection.xfilter.ui.util.PropertiesProxy;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Administrator
 */
public class ControlPanel extends JPanel {

    private static final Dimension uniteTextFieldDimension = new Dimension(200, 30);

    private final JLabel conditionLabel = new JLabel("输入筛选过滤条件: ");

    private final JPanel conditionInputPanel = new JPanel();

    private final JTextField conditionField = new JTextField();

    private final JButton addConditionButton = new JButton("添加条件");

    private final JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);

    private final JButton exitButton = new JButton("退出系统");

    private final JLabel darkLabel = new JLabel("夜间模式");

    private final JPanel darkButtonPanel = new JPanel();

    private final DarkModToggleButton switchDarkButton = new DarkModToggleButton();

    private final JPanel inPathFieldPanel = new JPanel();

    private final JLabel inFileChoosePrompt = new JLabel("请输入或者从下方按钮选择 输入文件 路径");

    private final JTextField inFilePathField = new JTextField();

    private final JButton openChooseInFileButton = new JButton("选择输入");

    private final JFileChooser inFileChooser = new JFileChooser(
            requireFile(PropertiesBundle.LAST_IN_FILE)
    );

    private final JPanel outPathFieldPanel = new JPanel();

    private final JLabel outFileChoosePrompt = new JLabel("请输入或者从下方按钮选择 输出文件 路径:");

    private final JTextField outFilePathField = new JTextField();

    private final JButton openChooseOutFileButton = new JButton("选择输出");

    private final JFileChooser outFileChooser = new JFileChooser(
            requireFile(PropertiesBundle.LAST_OUT_FILE)
    );

    private final JButton runButton = new JButton("go!");

    public ControlPanel() {
        init();
    }

    private void init() {
        this.setLayout(new GridLayout(8, 1, 5, 5));
        initToolBar();
        initAddFilterButtonGroup();
        initFileChooseGroup();
    }

    private void initAddFilterButtonGroup() {
        this.add(conditionLabel);
        this.add(conditionInputPanel);
        conditionField.setPreferredSize(uniteTextFieldDimension);
        conditionInputPanel.add(conditionField);
        conditionInputPanel.add(addConditionButton);
    }

    private void initFileChooseGroup() {
        this.add(inFileChoosePrompt);
        this.add(inPathFieldPanel);
        this.add(outFileChoosePrompt);
        this.add(outPathFieldPanel);
        this.add(runButton);

        inFilePathField.setPreferredSize(uniteTextFieldDimension);
        inPathFieldPanel.add(inFilePathField);
        inPathFieldPanel.add(openChooseInFileButton);

        outFilePathField.setPreferredSize(uniteTextFieldDimension);
        outPathFieldPanel.add(outFilePathField);
        outPathFieldPanel.add(openChooseOutFileButton);

    }

    private void initToolBar() {
        toolBar.setLayout(new GridLayout(1, 2, 20, 20));
        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        darkButtonPanel.add(darkLabel);
        darkButtonPanel.add(switchDarkButton);
        switchDarkButton.setPreferredSize(new Dimension(50, 20));
        toolBar.add(exitButton);
        toolBar.add(darkButtonPanel);
        this.add(toolBar);
    }

    /**
     * reset fields;
     */
    public void reset() {
        conditionField.setText(StringPool.EMPTY.value());
    }

    public String condition() {
        return String.valueOf(conditionField.getText()).trim();
    }

    public void chooseAndSave(ChooseFileType chooseFileType) {
        JTextField textField = null;
        JFileChooser fileChooser = null;
        PropertiesBundle bundle = null;
        switch (chooseFileType) {
            case IN:
                textField = inFilePathField;
                fileChooser = inFileChooser;
                bundle = PropertiesBundle.LAST_IN_FILE;
                break;
            case OUT:
                textField = outFilePathField;
                fileChooser = outFileChooser;
                bundle = PropertiesBundle.LAST_OUT_FILE;
                break;
            default:
                break;
        }
        int choice = fileChooser.showOpenDialog(null);
        switch (choice) {
            case JFileChooser.APPROVE_OPTION:
                textField.setText(NullSafe.safeObjToString(fileChooser.getSelectedFile()));
                break;
            case JFileChooser.CANCEL_OPTION:
            default:
                break;
        }
        if (fileChooser.getSelectedFile() != null) {
            PropertiesProxy.putAndSave(bundle.getLiteral(), String.valueOf(fileChooser.getSelectedFile()));
        }
    }

    private File requireFile(PropertiesBundle bundle) {
        File file = new File(String.valueOf(PropertiesProxy.getProperty(bundle.getLiteral())));
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public JButton getRunButton() {
        return runButton;
    }

    public JButton getAddConditionButton() {
        return addConditionButton;
    }

    public JButton getOpenChooseInFileButton() {
        return openChooseInFileButton;
    }

    public JButton getOpenChooseOutFileButton() {
        return openChooseOutFileButton;
    }

    public JTextField getInFilePathField() {
        return inFilePathField;
    }

    public JTextField getOutFilePathField() {
        return outFilePathField;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
