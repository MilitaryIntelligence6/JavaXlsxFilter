package cn.misection.xfilter.core.proxy;

import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName proxyable
 * @Description TODO
 * @CreateTime 2021年06月11日 16:59:00
 */
public interface XlsxProxyable {
    /**
     * 输出;
     */
    void output();

    /**
     * 获取数据;
     * @return
     */
    List<List<String>> data();
}
