package cn.misection.xfilter.core.nullobj;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName NullXlsx
 * @Description TODO
 * @CreateTime 2021年06月11日 16:08:00
 */
public class NullXlsx extends ArrayList<List<String>> {

    private volatile static NullXlsx instance = null;

    private NullXlsx() {

    }

    public static NullXlsx getInstance() {
        if (instance == null) {
            synchronized (NullXlsx.class) {
                if (instance == null) {
                    instance = new NullXlsx();
                }
            }
        }
        return instance;
    }
}
