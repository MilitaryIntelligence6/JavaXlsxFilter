package cn.misection.xfilter.ui.dao;

import cn.misection.xfilter.common.util.NullSafe;
import cn.misection.xfilter.ui.config.ResourceBundle;
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
     * @param conditionEntity
     */
    @Override
    public boolean addSave(ConditionEntity conditionEntity) {
        valueList.add(conditionEntity);
        return saveLast();
    }

    @Override
    public boolean removeSave() {
        return false;
    }

    @Override
    public boolean clearSave() {
        clearInstance();
        return clearDb();
    }

    @Override
    public List<ConditionEntity> value() {
        return valueList;
    }

    private void clearInstance() {
        valueList.clear();
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
    private boolean saveLast() {
        try {
            // user model
            ConditionEntity lastEntity = valueList.get(valueList.size() - 1);
            BufferedWriter saveWriter = new BufferedWriter(new FileWriter(dbFile, true));
            saveWriter.write(lastEntity.value());
            saveWriter.newLine();
            saveWriter.close();
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
}
