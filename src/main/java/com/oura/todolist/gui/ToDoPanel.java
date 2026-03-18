package com.oura.todolist.gui;

import com.oura.todolist.model.Task;
import com.oura.todolist.model.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ToDoPanel extends JPanel {
    private final TaskManager manager;
    private final JTextField inputField;
    private final JPanel listContainer;
    private final JComboBox<String> filterComboBox;

    private enum Filter {
        ALL, PENDING, COMPLETED
    }

    private Filter currentFilter = Filter.ALL;

    public ToDoPanel(TaskManager taskManager) {
        this.manager = taskManager;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputField = new JTextField();
        inputField.setFont(new Font("Droid Sans Tamil", Font.PLAIN, 16));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(_ -> addNewTask());

        inputField.addActionListener(_ -> addNewTask());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterComboBox = new JComboBox<>(new String[]{"All", "Pending", "Completed"});
        filterComboBox.addActionListener(_ -> {
            int selectedIndex = filterComboBox.getSelectedIndex();
            if (selectedIndex == 0) {
                currentFilter = Filter.ALL;
            } else if (selectedIndex == 1) {
                currentFilter = Filter.PENDING;
            } else {
                currentFilter = Filter.COMPLETED;
            }
            updateList();
        });
        filterPanel.add(new JLabel("Filter: "));
        filterPanel.add(filterComboBox);

        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clean Completed Tasks");
        clearButton.setForeground(Color.DARK_GRAY);
        clearButton.setFocusable(false);
        clearButton.addActionListener(_ -> {
            manager.clearCompletedTasks();
            updateList();
        });
        this.add(clearButton, BorderLayout.SOUTH);

        updateList();
    }

    private void addNewTask() {
        String text = inputField.getText();
        if (!text.trim().isEmpty()) {
            manager.addTask(new Task(text));
            inputField.setText("");
            updateList();
        }
    }

    private void updateList() {
        listContainer.removeAll();
        List<Task> allTasks = manager.getTasks();

        for (int i = 0; i < allTasks.size(); i++) {
            final int index = i;

            Task task = allTasks.get(i);

            if (currentFilter == Filter.PENDING && task.isCompleted()) continue;
            if (currentFilter == Filter.COMPLETED && !task.isCompleted()) continue;

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JCheckBox checkBox = getShowText(task, index);

            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

            JButton editButton = getEditButton(task, index);

            JButton upButton = new JButton("↑");
            upButton.setToolTipText("Move Up");
            upButton.setMargin(new Insets(2, 5, 2, 5));
            upButton.setFocusable(false);
            upButton.addActionListener(_ -> {
                manager.moveTaskUp(index);
                updateList();
            });
            if (currentFilter != Filter.ALL || index == 0) upButton.setEnabled(false);

            JButton downButton = new JButton("↓");
            downButton.setToolTipText("Move Down");
            downButton.setMargin(new Insets(2, 5, 2, 5));
            downButton.setFocusable(false);
            downButton.addActionListener(_ -> {
                manager.moveTaskDown(index);
                updateList();
            });

            if (currentFilter != Filter.ALL || index == allTasks.size() - 1) downButton.setEnabled(false);

            JButton deleteButton = new JButton("✖");
            deleteButton.setForeground(Color.RED);
            deleteButton.setToolTipText("Delete");
            deleteButton.setFocusable(false);
            deleteButton.setMargin(new Insets(2, 5, 2, 5));
            deleteButton.addActionListener(_ -> {
                manager.removeTask(index);
                updateList();
            });

            buttonsPanel.add(editButton);
            buttonsPanel.add(upButton);
            buttonsPanel.add(downButton);
            buttonsPanel.add(deleteButton);

            panel.add(checkBox, BorderLayout.CENTER);
            panel.add(buttonsPanel, BorderLayout.EAST);

            listContainer.add(panel);
        }
        listContainer.revalidate();
        listContainer.repaint();
    }

    private JButton getEditButton(Task task, int index) {
        JButton editButton = new JButton("✎");
        editButton.setToolTipText("Edit");
        editButton.setMargin(new Insets(2, 5, 2, 5));
        editButton.setFocusable(false);
        editButton.addActionListener(_ -> {
            String newTitle = JOptionPane.showInputDialog(this, "Edit task: ", task.getTitle());
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                manager.editTaskTitle(index, newTitle);
                updateList();
            }
        });
        return editButton;
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

        checkBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String newTitle = JOptionPane.showInputDialog(ToDoPanel.this, "Edit task: ", task.getTitle());
                    if (newTitle != null && !newTitle.trim().isEmpty()) {
                        manager.editTaskTitle(index, newTitle);
                        updateList();
                    }
                }
            }
        });

        checkBox.addActionListener(_ -> {
            manager.toggleTaskCompletion(index);
            updateList();
        });
        return checkBox;
    }
}