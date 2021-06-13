package cn.misection.xfilter.ui.dao;

import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.util.List;

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
    boolean saveAddChange(ConditionEntity condition);

    /**
     * 移除条件;
     * @param newData
     * @return
     */
    boolean saveRemoveChange(List<ConditionEntity> newData);

    /**
     * 清除所有;
     * @return
     */
    boolean saveClearChange();

    /**
     * 获取value;
     * TODO: 到 service;
     * @return
     */
    Object[] data();
}
