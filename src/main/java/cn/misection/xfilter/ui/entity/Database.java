package cn.misection.xfilter.ui.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private List<User> userList;

    public Database() {
        userList = new ArrayList<>();
    }

    /**
     * adds user to our collection;
     *
     * @param user
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * saves user to database file;
     *
     * @param file
     */
    public void saveUser(File file) {
        try {
            // user model
            User user;
            String save_data = "";

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            int i = 0;
            while (i < userList.size()) {
                user = userList.get(i);
                save_data = user.getFirstname() + ", " + user.getLastname();
                i++;
            }
            bufferedWriter.write(save_data);
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
