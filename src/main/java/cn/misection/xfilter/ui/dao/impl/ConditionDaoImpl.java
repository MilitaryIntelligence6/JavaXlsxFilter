package cn.misection.xfilter.ui.dao.impl;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.config.ResourceBundle;
import cn.misection.xfilter.ui.dao.ConditionDao;
import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
public class ConditionDaoImpl implements ConditionDao {

    private volatile static ConditionDaoImpl instance = null;

    private final File dbFile = requireDbFile();

    /**
     * TODO , 应该放到服务层去;
     */
    private final List<ConditionEntity> valueList = new LinkedList<>();

    private final Object[] rawData = loadOutFromDb();

    private ConditionDaoImpl() {
        init();
    }

    public static ConditionDaoImpl getInstance() {
        if (instance == null) {
            synchronized (ConditionDaoImpl.class) {
                if (instance == null) {
                    instance = new ConditionDaoImpl();
                }
            }
        }
        return instance;
    }

    private void init() {
        initValue();
    }

    private void initValue() {
        Object[] loadOutFromDb = loadOutFromDb();
        for (Object obj : loadOutFromDb) {
            valueList.add(new ConditionEntity(NullSafe.safeObjToString(obj)));
        }
    }

    /**
     * adds user to our collection;
     *
     * @param condition
     */
    @Override
    public boolean saveAddChange(ConditionEntity condition) {
        valueList.add(condition);
        return saveCondition(condition);
    }

    @Override
    public boolean saveRemoveChange(int index) {
        valueList.remove(index);
        return reSave(this.valueList);
    }

    @Override
    public boolean saveClearChange() {
        valueList.clear();
        return clearDb();
    }

    @Override
    public List<ConditionEntity> value() {
        return valueList;
    }

    private boolean clearDb() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, false));
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  reads user from database file;
     * @return
     */
    private Object[] loadOutFromDb() {
        Object[] conditionArray;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dbFile));
            conditionArray = reader.lines().toArray();
            reader.close();
            return conditionArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[0];
    }

    /**
     * saves user to database file;
     */
    private boolean saveCondition(ConditionEntity condition) {
        try {
            BufferedWriter saveWriter = new BufferedWriter(new FileWriter(dbFile, true));
            saveWriter.write(condition.value());
            saveWriter.newLine();
            saveWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean reSave(List<ConditionEntity> valueList) {
        try {
            BufferedWriter reSaveWriter = new BufferedWriter(new FileWriter(dbFile, false));
            for (ConditionEntity condition : valueList) {
                reSaveWriter.write(condition.value());
                reSaveWriter.newLine();
            }
            reSaveWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private File requireDbFile() {
        URL resource = ConditionDaoImpl.class.getResource(ResourceBundle.CONDITION_DB.getPath());
        if (resource != null) {
            String path = resource.getPath();
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
        return new File(ResourceBundle.CONDITION_DB.getPath());
    }

    public Object[] getRawData() {
        return rawData;
    }
}
