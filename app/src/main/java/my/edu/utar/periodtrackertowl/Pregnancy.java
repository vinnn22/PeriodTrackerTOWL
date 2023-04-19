package my.edu.utar.periodtrackertowl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class Pregnancy extends AppCompatActivity {

    private EditText ageEditText;
    private Button calculateButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy);

        ageEditText = findViewById(R.id.ageEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateProbability();

            }
        });

        // Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);
    }

    private void calculateProbability() {
        int age = Integer.parseInt(ageEditText.getText().toString());

        // Calculate the pregnancy probability based on the user's age and gender
        double probability = 0.0;

        if (age < 7) {
            probability =0.00;
        }else if (age >= 8 && age < 13) {
            probability = 0.15;
        }else if (age >= 14 && age <19) {
            probability = 0.90;
        } else if (age >= 20 && age < 24) {
            probability = 0.86;
        } else if (age >= 25 && age < 29) {
            probability = 0.78;
        } else if (age >= 30 && age < 34) {
            probability = 0.63;
        }else if (age >= 35 && age < 39) {
            probability = 0.52;
        }else if (age >= 40 && age < 68) {
            probability = 0.05;
        }else {
            probability = 0.00;
        }
        // Display the result
        String resultText = String.format(Locale.getDefault(), "Your pregnancy probability is %.2f%%", probability * 100);
        resultTextView.setText(resultText);


    }

}