package com.deva.tidy;

import java.sql.Timestamp;

public class Task implements Comparable<Task>{
    private String name;
    private Timestamp due;
    private boolean isDone;

    public Task(String name, Timestamp due){
        this.name = name;
        this.due = due;
        isDone = false;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDue() {
        return due;
    }

    public boolean isDone(){
        return isDone;
    }

    @Override
    public int compareTo(Task other) {
        return due.compareTo(other.due);
    }
}
