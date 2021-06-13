package cn.misection.xfilter.ui.service.impl;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.dao.ConditionDao;
import cn.misection.xfilter.ui.dao.impl.ConditionDaoImpl;
import cn.misection.xfilter.ui.entity.ConditionEntity;
import cn.misection.xfilter.ui.service.ConditionService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName ConditionServiceImpl
 * @Description TODO
 * @CreateTime 2021年06月12日 21:22:00
 */
public class ConditionServiceImpl implements ConditionService  {

    private volatile static ConditionServiceImpl instance = null;

    private ConditionDao dao = ConditionDaoImpl.getInstance();

    private final List<ConditionEntity> value = new LinkedList<>();

    private ConditionServiceImpl() {
        init();
    }

    public static ConditionServiceImpl getInstance() {
        if (instance == null) {
            synchronized (ConditionServiceImpl.class) {
                if (instance == null) {
                    instance = new ConditionServiceImpl();
                }
            }
        }
        return instance;
    }

    private void init() {
        initValue();
    }

    private void initValue() {
        Object[] loadOutFromDb = dao.data();
        for (Object obj : loadOutFromDb) {
            value.add(new ConditionEntity(NullSafe.safeObjToString(obj)));
        }
    }

    /**
     * adds user to our collection;
     *
     * @param condition
     */
    @Override
    public boolean add(ConditionEntity condition) {
        value.add(condition);
        return dao.saveAddChange(condition);
    }

    @Override
    public boolean remove(int index) {
        value.remove(index);
        return dao.saveRemoveChange(value);
    }

    @Override
    public boolean clear() {
        value.clear();
        return dao.saveClearChange();
    }

    @Override
    public List<ConditionEntity> value() {
        return value;
    }

}
