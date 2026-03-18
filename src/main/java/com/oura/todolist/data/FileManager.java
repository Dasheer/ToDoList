package com.oura.todolist.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oura.todolist.model.Task;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String USER_NAME = System.getProperty("user.home");
    private static final String APP_DIR_NAME = ".todolist";
    private static final String FILE_NAME = "tasks.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static File getFile() {
        File appDir = new File(USER_NAME, APP_DIR_NAME);
        if (!appDir.exists()) appDir.mkdirs();
        return new File(appDir, FILE_NAME);
    }

    public static void saveTasks(List<Task> tasks) {
        File file = getFile();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(tasks, writer);
        } catch (IOException ex) {
            System.err.println("Error saving tasks: " + ex.getMessage());
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                            "Error saving tasks: " + ex.getMessage(),
                            "Error saving",
                            JOptionPane.ERROR_MESSAGE)
            );
        }
    }

    public static List<Task> loadTasks() {
        File file = getFile();

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Task>>() {
            }.getType();
            List<Task> loadedTasks = gson.fromJson(reader, listType);

            return loadedTasks != null ? loadedTasks : new ArrayList<>();
        } catch (IOException ex) {
            System.err.println("Error loading tasks: " + ex.getMessage());
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                            "Error loading tasks: " + ex.getMessage(),
                            "Error loading",
                            JOptionPane.ERROR_MESSAGE)
            );
            return new ArrayList<>();
        }
    }
}
