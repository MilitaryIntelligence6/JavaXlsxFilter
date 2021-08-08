package cn.misection.xfilter.ui.bridge;

import cn.misection.xfilter.core.filter.Filterable;
import cn.misection.xfilter.core.filter.MapWordFilter;
import cn.misection.xfilter.core.proxy.XlsxFetchProxy;
import cn.misection.xfilter.core.proxy.XlsxProxyable;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.util.DialogPopper;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName CoreProxy
 * @Description TODO
 * @CreateTime 2021年06月13日 16:23:00
 */
public class CoreApiProxy {

    private final String inPath;

    private final String outPath;

    private final List<ConditionEntity> conditionList;

    private CoreApiProxy(Builder builder) {
        this.inPath = builder.inPath;
        this.outPath = builder.outPath;
        this.conditionList = builder.conditionList;
    }

    public boolean callFilter() {
        XlsxProxyable fetchProxy = null;
        try {
            fetchProxy = XlsxFetchProxy.requireDefaultFullSkip(inPath);
        } catch (IOException e) {
            e.printStackTrace();
            DialogPopper.error("读取xlsx文件失败, 请检查文件格式或者联系开发者debug");
            return false;
        }
        List<String> filterWordList = new ArrayList<String>() {{
            for (ConditionEntity condition : conditionList) {
                add(condition.value());
            }
        }};
        Filterable filter = MapWordFilter.requireDefaultStart(
                fetchProxy.data(),
                filterWordList,
                fetchProxy.safeSize()
        );
        // 处理数据捕获;
        try {
            filter.filter();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            DialogPopper.error(String.format("读取xlsx中数字失败, 问题出现在\n%s\n请检查文件格式或者联系开发者debug", e.getMessage()));
            return false;
        }
        // 输出捕获;
        try {
            fetchProxy.output(outPath);
        } catch (IOException e) {
            e.printStackTrace();
            DialogPopper.error("文件输出失败, 请检查是否开启文件, 并联系开发者debug");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            DialogPopper.unknownError();
        }
        return true;
    }

    public static class Builder {

        private String inPath;

        private String outPath;

        private List<ConditionEntity> conditionList;

        public CoreApiProxy build() {
            return new CoreApiProxy(this);
        }

        public Builder putInPath(String inPath) {
            this.inPath = inPath;
            return this;
        }

        public Builder putOutPath(String outPath) {
            this.outPath = outPath;
            return this;
        }

        public Builder putConditionList(List<ConditionEntity> conditionList) {
            this.conditionList = conditionList;
            return this;
        }
    }
}
