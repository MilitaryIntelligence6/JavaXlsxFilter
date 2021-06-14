package cn.misection.xfilter.ui.view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

/**
 * 最简单的Java拖拽代码示例
 *
 * @author 刘显安
 * 2013年1月24日
 */
public class DragTest extends JFrame {

    /**
     * 要接受拖拽的面板;
     */
    JPanel panel;

    public DragTest() {
        panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400, 200);
        setTitle("最简单的拖拽示例：拖拽文件到下面（20130124）");
        // 启用拖拽;
        registerDragListener();
    }

    public static void main(String[] args) {
        //设置皮肤;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new DragTest().setVisible(true);
    }

    /**
     * 定义的拖拽方法;
     */
    private void registerDragListener() {
        // panel表示要接受拖拽的控件
        new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            // 重写适配器的drop方法;
            @Override
            public void drop(DropTargetDropEvent targetDropEvent) {
                try {
                    //如果拖入的文件格式受支持;
                    if (targetDropEvent.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        //接收拖拽来的数据;
                        targetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List<File> list =
                                (List<File>) (targetDropEvent.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String temp = "";
                        for (File file : list) {
                            temp += file.getAbsolutePath() + ";\n";
                        }
                        JOptionPane.showMessageDialog(null, temp);
                        //指示拖拽操作已完成;
                        targetDropEvent.dropComplete(true);
                    } else {
                        //否则拒绝拖拽来的数据;
                        targetDropEvent.rejectDrop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
