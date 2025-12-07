package com.example.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private GymLogRepository repository;
    public static final String TAG = "DAC_GYMLOG";
    //This is a references to the viewBinding inside the app's build.gradle
    private ActivityMainBinding binding;

    //These are the mMembers that reference the labels we created in activity_main
    String exercise = " ";
    double weight = 0.0;
    int reps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        //Allows the user to scroll through their logs
        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymLogRecords();
                updateDisplay();
            }
        });
    }

    private void insertGymLogRecords(){
        GymLog log = new GymLog(reps, weight, exercise);
        repository.insertGymLog(log);
    }

    /**
     * This method will try to pull the information we receive as input from the application screen.
     */
    public void getInformationFromDisplay() {
        //[label member] = [reference.values entered from Exercise __________]
        exercise = binding.exerciseInputEditText.getText().toString();

        try {
            //The parseDouble takes the input String from Weight____ and converts it to a double
            weight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight in edit text.");

        }
        try {
            //The parseDouble takes the input String from Weight____ and converts it to a double
            reps = Integer.parseInt(binding.repInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Reps in edit text.");

        }
    }

    /**
     * This method updates the display by taking the current info, setting up a new display that
     * will format how the results will appear, and outputs the input-given information.
     */
    public void updateDisplay() {
        String currentInfo = binding.logDisplayTextView.getText().toString();
        Log.d(TAG, "current info: " + currentInfo);
        String newDisplay = String.format(Locale.US,
                                   "Exercise: %s%nWeight: %.2f%nReps: %d%n=-=-=-=%n%s",
                                          exercise, weight, reps, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
        Log.i(TAG, repository.getAllLogs().toString());
    }
}