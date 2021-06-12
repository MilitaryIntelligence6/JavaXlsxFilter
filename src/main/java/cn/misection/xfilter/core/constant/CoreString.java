package cn.misection.xfilter.core.constant;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName CoreString
 * @Description TODO
 * @CreateTime 2021年06月12日 22:43:00
 */
public enum CoreString {

    /**
     * 常用 String;
     */
    TOTAL_SUM_KEY("@全部持股总和$"),
    ;

    private final String value;

    CoreString(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
