package cn.misection.xfilter.common.util;

import cn.misection.xfilter.common.constant.StringPool;

import java.net.URL;

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

    public static String safeString(String string) {
        return string == null
                ? StringPool.EMPTY.value()
                : string.trim();
    }

    public static String safeObjToString(Object obj) {
        return obj == null
                ? StringPool.EMPTY.value()
                : String.valueOf(obj).trim();
    }

    public static String safeDoubleString(String string) {
        if (string == null || string.isEmpty()) {
            return "0";
        }
        return string;
    }
}
