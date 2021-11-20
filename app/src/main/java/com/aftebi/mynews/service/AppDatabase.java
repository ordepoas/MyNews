package com.aftebi.mynews.service;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aftebi.mynews.model.Task;

@Database(entities = { Task.class }, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public AppDatabase() {};

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).fallbackToDestructiveMigration().build();
    }

    public abstract TaskDAO getRepoDao();
}
