package com.aftebi.mynews.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "description")
    private String Description;
    @ColumnInfo (name = "isdone")
    private boolean isDone;
    @ColumnInfo(name = "priority")
    private Priority priority;

    public Task() {
    }

    public Task(String description, boolean isDone, Priority priority) {
        Description = description;
        this.isDone = isDone;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
