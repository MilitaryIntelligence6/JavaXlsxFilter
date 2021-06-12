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
     * @param condition
     * @return
     */
    boolean add(String condition);

    /**
     * 移除条件;
     * @param condition
     * @return
     */
    boolean remove(String condition);
}
