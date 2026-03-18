package com.oura.todolist;

import com.oura.todolist.data.FileManager;
import com.oura.todolist.gui.ToDoWindow;
import com.formdev.flatlaf.FlatLightLaf;
import com.oura.todolist.model.Task;
import com.oura.todolist.model.TaskManager;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                System.err.println("Failed to set FlatLaf look and feel");
            }

            List<Task> savedTasks = FileManager.loadTasks();
            TaskManager manager = new TaskManager();
            manager.setTasks(savedTasks);

            new ToDoWindow(manager);
        });
    }
}
