package cn.misection.xfilter.ui.dao;

import cn.misection.xfilter.ui.entity.ConditionEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class Database {

    private final List<ConditionEntity> conditionEntityList;

    public Database() {
        conditionEntityList = new ArrayList<>();
    }

    /**
     * adds user to our collection;
     *
     * @param conditionEntity
     */
    public void addCondition(ConditionEntity conditionEntity) {
        conditionEntityList.add(conditionEntity);
    }

    /**
     * saves user to database file;
     *
     * @param file
     */
    public void saveCondition(File file) {
        try {
            // user model
            ConditionEntity conditionEntity;
            String saveData = "";

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            int i = 0;
            while (i < conditionEntityList.size()) {
                conditionEntity = conditionEntityList.get(i);
                saveData = conditionEntity.value();
                i++;
            }
            bufferedWriter.write(saveData);
            bufferedWriter.newLine();
            // prevents memory leak
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  reads user from database file;
     * @param file
     * @return
     */
    public Object[] loadUsers(File file) {
        Object[] objects;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            // each lines to array
            objects = bufferedReader.lines().toArray();
            bufferedReader.close();
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
