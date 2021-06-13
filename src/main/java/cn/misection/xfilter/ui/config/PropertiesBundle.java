package cn.misection.xfilter.ui.config;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName Properties
 * @Description TODO
 * @CreateTime 2021年06月13日 13:30:00
 */
public enum PropertiesBundle {

    /**
     * 配置文件;
     */
    /**
     * 夜间模式;
     */
    DARK("dark"),
    ;

    private final String literal;

    PropertiesBundle(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
