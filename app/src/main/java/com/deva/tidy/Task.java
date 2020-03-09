package com.deva.tidy;

import java.io.Serializable;
import java.sql.Timestamp;

public class Task implements Comparable<Task>, Serializable {
    private int id;
    private String name;
    private Timestamp due;
    private boolean isDone;

    public Task(String name, Timestamp due){
        this.name = name;
        this.due = due;
        isDone = false;
    }
    public Task(String name, Timestamp due, boolean isDone){
        this.name = name;
        this.due = due;
        this.isDone = isDone;
    }
    public Task(String name, Timestamp due, boolean isDone, int id){
        this.name = name;
        this.due = due;
        this.isDone = isDone;
        this.id = id;
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
    public void setDone(boolean done){
        this.isDone = done;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public int compareTo(Task other) {
        return due.compareTo(other.due);
    }
}
