package cn.misection.xfilter.ui.view.component;

import cn.misection.xfilter.common.constant.StringPool;
import cn.misection.xfilter.ui.view.ControlPanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author Administrator
 */
public class DragDropTargetListener implements DropTargetListener {

    protected ControlPanel controlPanel;

    protected DropTarget dropTarget;

    /**
     * Indicates whether data is acceptable
     */
    protected boolean acceptableType;

    public DragDropTargetListener(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        // Create the DropTarget and register
        // it with the JEditorPane.
        dropTarget = new DropTarget(controlPanel, DnDConstants.ACTION_COPY_OR_MOVE,
                this, true, null);
    }

    /**
     * Implementation of the DropTargetListener interface;
     * @param dtde
     */
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln(String.format("dragEnter, drop action = %s", DnDUtils.showActions(dtde.getDropAction())));
        // Get the type of object being transferred and determine
        // whether it is appropriate.
        checkTransferType(dtde);
        // Accept or reject the drag.
        acceptOrRejectDrag(dtde);
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        DnDUtils.debugPrintln("DropTarget dragExit");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln(String.format("DropTarget dragOver, drop action = %s",
                DnDUtils.showActions(dtde.getDropAction())));

        // Accept or reject the drag
        acceptOrRejectDrag(dtde);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        DnDUtils.debugPrintln(String.format("DropTarget dropActionChanged, drop action = %s",
                DnDUtils.showActions(dtde.getDropAction())));
        // Accept or reject the drag
        acceptOrRejectDrag(dtde);
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        DnDUtils.debugPrintln(String.format("DropTarget drop, drop action = %s",
                DnDUtils.showActions(dtde.getDropAction())));
        // Check the drop action
        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
            // Accept the drop and get the transfer data
            dtde.acceptDrop(dtde.getDropAction());
            Transferable transferable = dtde.getTransferable();

            try {
                boolean result = dropFile(transferable);

                dtde.dropComplete(result);
                DnDUtils.debugPrintln("Drop completed, success: " + result);
            } catch (Exception e) {
                DnDUtils.debugPrintln("Exception while handling drop " + e);
                dtde.dropComplete(false);
            }
        } else {
            DnDUtils.debugPrintln("Drop target rejected drop");
            dtde.rejectDrop();
        }
    }

    /**
     * Internal methods start here;
     * @param targetDragEvent
     * @return
     */
    protected boolean acceptOrRejectDrag(DropTargetDragEvent targetDragEvent) {
        int dropAction = targetDragEvent.getDropAction();
        int sourceActions = targetDragEvent.getSourceActions();
        boolean acceptedDrag = false;
        DnDUtils.debugPrintln(String.format("/tSource actions are %s, drop action is %s",
                DnDUtils.showActions(sourceActions), DnDUtils.showActions(dropAction)));

        // Reject if the object being transferred
        // or the operations available are not acceptable.
        if (!acceptableType
                || (sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
            DnDUtils.debugPrintln("Drop target rejecting drag");
            targetDragEvent.rejectDrag();
        } else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
            // Not offering copy or move - suggest a copy
            DnDUtils.debugPrintln("Drop target offering COPY");
            targetDragEvent.acceptDrag(DnDConstants.ACTION_COPY);
            acceptedDrag = true;
        } else {
            // Offering an acceptable operation: accept
            DnDUtils.debugPrintln("Drop target accepting drag");
            targetDragEvent.acceptDrag(dropAction);
            acceptedDrag = true;
        }

        return acceptedDrag;
    }

    protected void checkTransferType(DropTargetDragEvent dtde) {
        // Only accept a list of files
        acceptableType = dtde
                .isDataFlavorSupported(DataFlavor.javaFileListFlavor);

        DnDUtils.debugPrintln("File type acceptable - " + acceptableType);
    }

    /**
     * This method handles a drop for a list of files;
     * @param transferable
     * @return
     * @throws IOException
     * @throws UnsupportedFlavorException
     * @throws MalformedURLException
     */
    protected boolean dropFile(Transferable transferable) {
        List<File> fileList = null;
        try {
            fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        File transferFile = null;
        if (fileList != null && fileList.size() >= 1) {
            transferFile = fileList.get(0);
        }
        if (transferFile != null) {
            controlPanel.getInFilePathField().setText(transferFile.getAbsolutePath());
        }
        return true;
    }
}

class DnDUtils {
    public static String showActions(int action) {
        String actions = StringPool.EMPTY.value();
        if ((action & (DnDConstants.ACTION_LINK | DnDConstants.ACTION_COPY_OR_MOVE)) == 0) {
            return "None";
        }
        if ((action & DnDConstants.ACTION_COPY) != 0) {
            actions += "Copy ";
        }
        if ((action & DnDConstants.ACTION_MOVE) != 0) {
            actions += "Move ";
        }
        if ((action & DnDConstants.ACTION_LINK) != 0) {
            actions += "Link";
        }
        return actions;
    }

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void debugPrintln(String s) {
        if (debugEnabled) {
            System.err.println(s);
        }
    }

    private static boolean debugEnabled = (System
            .getProperty("DnDExamples.debug") != null);
}
