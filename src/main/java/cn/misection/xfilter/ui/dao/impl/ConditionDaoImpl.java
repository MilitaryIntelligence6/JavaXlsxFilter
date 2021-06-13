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

    private final Object[] rawData = loadOutFromDb();

    private ConditionDaoImpl() {
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

    /**
     * adds user to our collection;
     *
     * @param condition
     */
    @Override
    public boolean saveAddChange(ConditionEntity condition) {
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

    @Override
    public boolean saveRemoveChange(List<ConditionEntity> newData) {
        try {
            BufferedWriter reSaveWriter = new BufferedWriter(new FileWriter(dbFile, false));
            for (ConditionEntity condition : newData) {
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

    @Override
    public boolean saveClearChange() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, false));
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object[] data() {
        return rawData;
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
