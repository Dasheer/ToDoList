package com.oura.todolist.data;

import com.oura.todolist.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "tasks.txt";

    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.getTitle() + " | " + task.isCompleted());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error saving tasks: " + ex.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        List<Task> loadedTasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return loadedTasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 2) {
                    String title = parts[0];
                    boolean isCompleted = Boolean.parseBoolean(parts[1]);

                    Task task = new Task(title);
                    task.setCompleted(isCompleted);
                    loadedTasks.add(task);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading tasks: " + ex.getMessage());
        }
        return loadedTasks;
    }
}
