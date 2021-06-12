package cn.misection.xfilter.ui.config;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ResourcePath
 * @Description TODO
 * @CreateTime 2021年06月12日 20:49:00
 */
public enum ResourceBundle {

    /**
     * 配置, 数据库等路径;
     */
    CONFIG("/properties/config.properties"),

    CONDITION_DB("/db/condition.db"),
    ;

    private final String path;

    ResourceBundle(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}