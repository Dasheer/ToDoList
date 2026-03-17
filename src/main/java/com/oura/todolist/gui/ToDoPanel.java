package com.oura.todolist.gui;

import com.oura.todolist.model.Task;
import com.oura.todolist.model.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ToDoPanel extends JPanel {
    private TaskManager manager;
    private JTextField inputField;
    private JPanel listContainer;

    public ToDoPanel(TaskManager taskManager) {
        this.manager = taskManager;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new BorderLayout(5, 0));
        inputField = new JTextField();
        inputField.setFont(new Font("Droid Sans Tamil", Font.PLAIN, 16));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addNewTask());

        inputField.addActionListener(e -> addNewTask());

        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

        listContainer = new JPanel();

        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clean Completed Tasks");
        clearButton.setForeground(Color.DARK_GRAY);
        clearButton.setFocusable(false);
        clearButton.addActionListener(e -> {
            manager.clearCompletedTasks();
            updateList();
        });
        this.add(clearButton, BorderLayout.SOUTH);

        updateList();
    }

    private void addNewTask() {
        String text = inputField.getText();
        if (!text.trim().isEmpty()) {
            manager.addTask(text);
            inputField.setText("");
            updateList();
        }
    }

    private void updateList() {
        listContainer.removeAll();
        List<Task> tasks = manager.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            final int index = i;

            Task task = tasks.get(i);

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JCheckBox checkBox = getShowText(task, index);

            JButton deleteButton = new JButton("X");
            deleteButton.setForeground(Color.RED);
            deleteButton.setFocusable(false);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);

            deleteButton.addActionListener(e -> {
                manager.removeTask(index);
                updateList();
            });

            panel.add(checkBox, BorderLayout.CENTER);
            panel.add(deleteButton, BorderLayout.EAST);

            listContainer.add(panel);
        }
        listContainer.revalidate();
        listContainer.repaint();
    }

    private JCheckBox getShowText(Task task, int index) {
        String showText = task.getTitle();
        if (task.isCompleted()) {
            showText = "<html><s><font color='gray'>" + showText + "</font></s></html>";
        } else {
            showText = "<html><font color='black'>" + showText + "</font></html>";
        }

        JCheckBox checkBox = new JCheckBox(showText, task.isCompleted());
        checkBox.setFont(new Font("Droid Sans Tamil", task.isCompleted() ? Font.ITALIC : Font.PLAIN, 16));

        checkBox.addActionListener(e -> {
            manager.toggleTaskCompletion(index);
            updateList();
        });
        return checkBox;
    }
}