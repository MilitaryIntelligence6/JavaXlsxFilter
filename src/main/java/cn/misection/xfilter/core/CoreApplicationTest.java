package cn.misection.xfilter.core;

import cn.misection.xfilter.core.constant.TestFile;
import cn.misection.xfilter.core.constant.TestOutputFile;
import cn.misection.xfilter.core.filter.Filterable;
import cn.misection.xfilter.core.filter.MapWordFilter;
import cn.misection.xfilter.core.proxy.XlsxFetchProxy;
import cn.misection.xfilter.core.proxy.XlsxProxyable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName Application
 * @Description TODO
 * @CreateTime 2021年06月11日 17:31:00
 */
public class CoreApplicationTest {
    /**
     * @Test
     */
    public static void main(String[] args) {
        XlsxProxyable fetchProxy = XlsxFetchProxy
                .requireDefaultFullSkip(TestFile.PATH);
        System.out.println(fetchProxy.data());
        List<String> filterWordList = new ArrayList<String>(){{
            add("国有法人");
        }};
        Filterable filter = MapWordFilter.requireDefaultStart(
                fetchProxy.data(),
                filterWordList
        );
        filter.filter();
        System.out.println(fetchProxy.data());
        fetchProxy.output(TestOutputFile.PATH);
    }
}
