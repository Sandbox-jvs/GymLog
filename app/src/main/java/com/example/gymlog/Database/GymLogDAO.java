package com.example.gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.gymlog.database.entities.GymLog;
import java.util.List;

/**
 * Represents queries we will run from the database
 * @author Jessica Sandoval
 * @since 12/02/2025
 */

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);

    @Query("Select * from " + GymLogDatabase.GYM_LOG_TABLE)
    List<GymLog> getAllRecords();
}
