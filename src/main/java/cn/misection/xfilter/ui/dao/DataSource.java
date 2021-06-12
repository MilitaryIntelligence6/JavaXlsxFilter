package cn.misection.xfilter.ui.dao;

import cn.misection.xfilter.ui.config.ResourceBundle;
import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
public class DataSource {

    private volatile static DataSource instance = null;

    private static final File dbFile = requireDbFile();

    private static final BufferedWriter bufWriter = requireBuf();

    private final List<ConditionEntity> entityList = new LinkedList<>();

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DataSource();
                }
            }
        }
        return instance;
    }

    /**
     *  reads user from database file;
     * @return
     */
    public Object[] loadOut() {
        Object[] objects;
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(dbFile));
            // each lines to array
            objects = bufReader.lines().toArray();
            bufReader.close();
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * adds user to our collection;
     *
     * @param conditionEntity
     */
    public void addAndSave(ConditionEntity conditionEntity) {
        entityList.add(conditionEntity);
        saveLast();
    }

    /**
     * saves user to database file;
     */
    private void saveLast() {
        try {
            // user model
            ConditionEntity lastEntity =
                    entityList.get(entityList.size() - 1);
            bufWriter.write(lastEntity.value());
            bufWriter.newLine();
            // prevents memory leak
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File requireDbFile() {
        URL resource = DataSource.class.getResource(ResourceBundle.CONDITION_DB.getPath());
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

    private static BufferedWriter requireBuf() {
        try {
            return new BufferedWriter(new FileWriter(dbFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
