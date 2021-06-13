package cn.misection.xfilter.ui.dao;

import cn.misection.xfilter.ui.config.ResourceBundle;
import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
public class DataSource {

    private volatile static DataSource instance = null;

    private final File dbFile = requireDbFile();

    private final BufferedReader bufReader = requireBufReader();

    private final BufferedWriter bufWriter = requireBufWriter();

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
    public Object[] loadOutOnlyOnceFromDb() {
        Object[] objects;
        try {
            // each lines to array
            objects = bufReader.lines().toArray();
            bufReader.close();
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[0];
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

    public void clearData() {
        clearInstance();
        clearDb();
    }

    private void clearInstance() {
        entityList.clear();
    }

    private void clearDb() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, false));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves user to database file;
     */
    private void saveLast() {
        try {
            // user model
            ConditionEntity lastEntity = entityList.get(entityList.size() - 1);
            BufferedWriter saveWriter = new BufferedWriter(new FileWriter(dbFile, true));
            saveWriter.write(lastEntity.value());
            saveWriter.newLine();
            saveWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File requireDbFile() {
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


    private BufferedReader requireBufReader() {
        try {
            return new BufferedReader(new FileReader(dbFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedWriter requireBufWriter() {
        try {
            return new BufferedWriter(new FileWriter(dbFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
