package com.example.itemmanagement.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.itemmanagement.model.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
}