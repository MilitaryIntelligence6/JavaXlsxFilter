package cn.misection.xfilter.ui.bridge;

import cn.misection.xfilter.core.filter.Filterable;
import cn.misection.xfilter.core.filter.MapWordFilter;
import cn.misection.xfilter.core.proxy.XlsxFetchProxy;
import cn.misection.xfilter.core.proxy.XlsxProxyable;
import cn.misection.xfilter.ui.entity.ConditionEntity;

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

    private String inPath;

    private String outPath;

    private List<ConditionEntity> conditionList;

    private CoreApiProxy(Builder builder) {
        this.inPath = builder.inPath;
        this.outPath = builder.outPath;
        this.conditionList = builder.conditionList;
    }

    public void callFilter() {
        XlsxProxyable fetchProxy = XlsxFetchProxy.requireDefaultFullSkip(inPath);
        List<String> filterWordList = new ArrayList<String>() {{
            for (ConditionEntity condition : conditionList) {
                add(condition.value());
            }
        }};
        Filterable filter = MapWordFilter.requireDefaultStart(
                fetchProxy.data(),
                filterWordList
        );
        filter.filter();
        fetchProxy.output(outPath);
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
