package com.oura.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    public void setTasks(List<Task> tasks) {
        if (tasks != null) {
            this.tasks = tasks;
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void toggleTaskCompletion(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setCompleted(!task.isCompleted());
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public void clearCompletedTasks() {
        tasks.removeIf(Task::isCompleted);
    }

    public void moveTaskUp(int index) {
        if (index > 0 && index < tasks.size()) {
            Task task = tasks.remove(index);
            tasks.add(index - 1, task);
        }
    }

    public void moveTaskDown(int index) {
        if (index >= 0 && index < tasks.size() - 1) {
            Task task = tasks.remove(index);
            tasks.add(index + 1, task);
        }
    }

    public void editTaskTitle(int index, String newTitle) {
        if (index >= 0 && index < tasks.size() && newTitle != null && !newTitle.trim().isEmpty()) {
            tasks.get(index).setTitle(newTitle);
        }
    }
}
