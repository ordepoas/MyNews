package com.aftebi.mynews.service;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aftebi.mynews.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM Task")
    List<Task> getAll();
    /*
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);
    */
    @Insert
    void insertAll(Task... users);

    @Update
    void update(Task t);


    @Delete
    void delete(Task t);


}
