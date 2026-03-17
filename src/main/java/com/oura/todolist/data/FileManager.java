package com.oura.todolist.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.oura.todolist.model.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "tasks.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(tasks, writer);
        } catch (IOException ex) {
            System.out.println("Error saving tasks: " + ex.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Task>>() {
            }.getType();
            List<Task> loadedTasks = gson.fromJson(reader, listType);

            return loadedTasks != null ? loadedTasks : new ArrayList<>();
        } catch (IOException ex) {
            System.out.println("Error loading tasks: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
}
