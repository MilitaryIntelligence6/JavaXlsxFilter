package cn.misection.xfilter.ui.constant;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName StringPool
 * @Description TODO
 * @CreateTime 2021年06月13日 20:18:00
 */
public enum StringPool {
    /**
     * String 池;
     */
    SUFFIX_XLSX(".xlsx"),

    SUFFIX_XLS(".xls"),
    ;

    private final String value;

    StringPool(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
