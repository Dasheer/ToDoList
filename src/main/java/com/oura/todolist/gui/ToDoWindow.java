package com.oura.todolist.gui;

import com.oura.todolist.data.FileManager;
import com.oura.todolist.model.TaskManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ToDoWindow extends JFrame {
    private final TaskManager manager;

    public ToDoWindow(TaskManager manager) {
        this.manager = manager;

        setTitle("Oura - ToDo List");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ToDoPanel panel = new ToDoPanel(manager);
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileManager.saveTasks(manager.getTasks());
                System.exit(0);
            }
        });
        setVisible(true);
    }
}
