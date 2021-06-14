package cn.misection.xfilter.core.proxy;

import java.io.IOException;
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
     * @param outPath
     * @throws IOException
     */
    void output(String outPath) throws IOException;

    /**
     * 获取数据;
     * @return
     */
    List<List<String>> data();

    /**
     * 安全容量;
     * @return
     */
    int safeSize();
}
