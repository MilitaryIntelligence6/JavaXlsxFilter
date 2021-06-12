package cn.misection.xfilter.core.proxy;

import cn.misection.xfilter.core.constant.TestOutputFile;
import cn.misection.xfilter.core.nullobj.NullXlsx;
import com.github.crab2died.ExcelUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName TestReader
 * @Description TODO
 * @CreateTime 2021年06月11日 15:23:00
 */
public class XlsxFetchProxy implements XlsxProxyable {

    private final String path;

    private final int skipHeadCount;

    private final int skipTailCount;

    private List<List<String>> dataLineList;

    public XlsxFetchProxy(String path,
                          int skipHeadCount,
                          int skipTailCount) {
        this.path = path;
        this.skipHeadCount = skipHeadCount;
        this.skipTailCount = skipTailCount;
        init();
    }

    public static XlsxFetchProxy requireDefaultFullSkip(String path) {
        return new XlsxFetchProxy(path, 1, 1);
    }

    public static XlsxFetchProxy requireDefaultNoSkip(String path) {
        return new XlsxFetchProxy(path, 0, 0);
    }

    private void init() {
        initDataLineList();
    }

    private void initDataLineList() {
        this.dataLineList = requireDataLineList();
    }

    @Override
    public void output() {
        try {
            ExcelUtils.getInstance()
                    .exportObjects2Excel(
                            this.dataLineList,
                            TestOutputFile.PATH
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<List<String>> data() {
        return dataLineList;
    }

    private List<List<String>> requireDataLineList() {
        List<List<String>> lineList = NullXlsx.getInstance();
        try {
            lineList = ExcelUtils.getInstance()
                    .readExcel2List(path);
            // 跳过头, 扔掉第一个;
            if (skipHeadCount > 0 && lineList.size() >= skipHeadCount) {
                lineList.subList(0, skipHeadCount).clear();
            }
            // 跳过尾巴, 扔掉最后一个;
            if (skipTailCount > 0 && lineList.size() >= skipTailCount) {
                // 需要测试;
                lineList.subList(lineList.size() - skipTailCount, lineList.size() - 1).clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineList;
    }

    public List<List<String>> getDataLineList() {
        return dataLineList;
    }

    public void setDataLineList(List<List<String>> dataLineList) {
        this.dataLineList = dataLineList;
    }
}
