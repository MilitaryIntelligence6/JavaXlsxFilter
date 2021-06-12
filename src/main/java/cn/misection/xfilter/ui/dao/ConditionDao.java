package cn.misection.xfilter.ui.dao;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ConditionDao
 * @Description TODO
 * @CreateTime 2021年06月12日 20:32:00
 */
public interface ConditionDao {

    /**
     * 添加条件;
     */
    void add();

    /**
     * 移除条件 by key;
     * @param key
     */
    void remove(String key);
}
