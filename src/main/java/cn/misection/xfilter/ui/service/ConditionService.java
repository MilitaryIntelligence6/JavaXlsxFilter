package cn.misection.xfilter.ui.service;

import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ConditonService
 * @Description TODO
 * @CreateTime 2021年06月12日 20:55:00
 */
public interface ConditionService {

    /**
     * 添加条件;
     * @param condition
     * @return
     */
    boolean add(ConditionEntity condition);

    /**
     * 移除条件;
     * @param index
     * @return
     */
    boolean remove(int index);

    /**
     * 清除所有;
     * @return
     */
    boolean clear();

    /**
     * 获取value;
     * TODO: 到 service;
     * @return
     */
    List<ConditionEntity> value();
}
