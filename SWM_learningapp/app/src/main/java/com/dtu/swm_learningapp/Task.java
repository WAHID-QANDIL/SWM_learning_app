package com.dtu.swm_learningapp;



public class Task {
    private String taskName;
    private boolean isDone;
    private String priority;

    public Task(String taskName, String priority) {
        this.taskName = taskName;
        this.isDone = false;
        this.priority = priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
