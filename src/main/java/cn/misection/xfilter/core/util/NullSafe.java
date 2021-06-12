package cn.misection.xfilter.core.util;

import cn.misection.xfilter.core.constant.StringPool;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName SafeStringUtil
 * @Description TODO
 * @CreateTime 2021年06月11日 21:02:00
 */
public class NullSafe {

    private NullSafe() {
        throw new RuntimeException("here are no NullSafe instance for you");
    }

    public static String requireSafeString(String string) {
        return string == null
                ? StringPool.EMPTY.value()
                : string.trim();
    }
}
