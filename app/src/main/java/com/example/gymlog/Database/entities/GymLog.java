package com.example.gymlog.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gymlog.database.GymLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jessica Sandoval
 * @since 12/02/2025
 */

//Takes the static reference from GymLogDatabase as the table name
@Entity(tableName = GymLogDatabase.GYM_LOG_TABLE)
public class GymLog {
    //auto generation ensures that primary key will never be null
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exercise;
    private double weight;
    private int reps;
    LocalDateTime date;

    public GymLog(int reps, double weight, String exercise) {
        this.reps = reps;
        this.weight = weight;
        this.exercise = exercise;
        //Will get the current time
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return exercise + '\n' +
                "Weight: " + weight + '\n' +
                "Reps: " + reps + '\n' +
                "Date: " + date.toString() + '\n' +
                "=-=-=-=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id && Double.compare(weight, gymLog.weight) == 0 && reps == gymLog.reps && Objects.equals(exercise, gymLog.exercise) && Objects.equals(date, gymLog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
