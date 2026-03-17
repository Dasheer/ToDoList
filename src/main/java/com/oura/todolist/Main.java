package com.oura.todolist;

import com.oura.todolist.data.FileManager;
import com.oura.todolist.gui.ToDoWindow;
import com.formdev.flatlaf.FlatLightLaf;
import com.oura.todolist.model.Task;
import com.oura.todolist.model.TaskManager;

import javax.swing.*;
import java.util.List;

public class Main {
    static void main() {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to set FlatLaf look and feel");
        }

        List<Task> tasksSaves = FileManager.loadTasks();

        TaskManager manager = new TaskManager();
        for (Task t : tasksSaves) {
            manager.addTask(t.getTitle());
            if (t.isCompleted()) {
                manager.toggleTaskCompletion(manager.getTasks().size() - 1);
            }
        }

        SwingUtilities.invokeLater(() -> {
            new ToDoWindow(manager);
        });
    }
}
