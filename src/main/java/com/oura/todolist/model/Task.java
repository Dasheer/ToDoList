package com.oura.todolist.model;

public class Task {
    public enum Priority {
        LOW, MEDIUM, HIGH
    }
    private String title;
    private boolean isCompleted;
    private Priority priority;
    private String dueDate;

    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
        this.priority = Priority.MEDIUM;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[x] " : "[ ] ") + title;
    }
}
