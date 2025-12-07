package com.example.gymlog.database;
import android.app.Application;
import android.util.Log;
import com.example.gymlog.MainActivity;
import com.example.gymlog.database.entities.GymLog;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 d* @author Jessica Sandoval
 * @since 12/02/2025
 */


public class GymLogRepository {
    private static GymLogRepository repository;
    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;

    /**
     * Constructor can only be called here due to private access modifier and is accessed
     * by the getRepository() method.
     * @param application
     */
    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    /**
     * This ensures that there's only on instance of the Gym Log repository and is accessed
     * through this method.
     * @param application
     * @return repository
     */
    public static GymLogRepository getRepository(Application application) {
        if(repository != null) {
            return repository;
        }
        Future<GymLogRepository> future= GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    /**
     * This method is a way for us to use the getAllRecords() method declared in the DAO
     * @return
     */
    public ArrayList<GymLog> getAllLogs() {
        //A promise that a value will, sometime in the future, here that we'll need to check on
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog) {
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymLogDAO.insert(gymLog);
        });
    }
}
