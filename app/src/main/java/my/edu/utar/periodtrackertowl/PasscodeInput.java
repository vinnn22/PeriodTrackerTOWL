package my.edu.utar.periodtrackertowl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class PasscodeInput extends AppCompatActivity {
    private EditText passcodeEditText;
    private Switch otherSwitch;
    private boolean switchOn;
    String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_input);

        passcodeEditText = findViewById(R.id.passcode_input);
        Button submitButton = findViewById(R.id.submit_button);

        LayoutInflater inflater = LayoutInflater.from(this);
        View otherLayout = inflater.inflate(R.layout.activity_password, null);
        otherSwitch = otherLayout.findViewById(R.id.passcode_switch);
// Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);

        // Check the state of the switch
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        switchOn = sharedPreferences.getBoolean("switchOn", false);

        // Set the switch to the correct state
        otherSwitch.setChecked(switchOn);

        if (switchOn) {
            String storedPasscode = getStoredPasscode();
            if (storedPasscode.isEmpty()) {
                // No passcode has been set yet, so start the Password activity to set one
                Intent intent = new Intent(PasscodeInput.this, login.class);
                startActivity(intent);
                finish();
            } else {
                // A passcode has been set, so prompt the user to enter it
                passcodeEditText.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
            }
        } else {
            // If the switch is off, start the main activity immediately
            Intent intent = new Intent(PasscodeInput.this, login.class);
            startActivity(intent);
            finish();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchOn) {
                    String storedPasscode = getStoredPasscode();
                    String enteredPasscode = passcodeEditText.getText().toString();
                    if (enteredPasscode.equals(storedPasscode)) {
                        // Start the MainActivity
                        Intent intent = new Intent(PasscodeInput.this, login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Display an error message
                        Toast.makeText(PasscodeInput.this, "Incorrect passcode", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        otherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchOn = isChecked;
                // Save the state of the switch to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switchOn", switchOn);
                editor.apply();
            }
        });
        loadLocale();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(wrapContext(newBase));
    }

    private Context wrapContext(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = prefs.getString("language", "");
        if (lang.isEmpty()) {
            return context;
        } else {
            Locale newLocale = new Locale(lang);
            Configuration config = context.getResources().getConfiguration();
            config.setLocale(newLocale);
            return context.createConfigurationContext(config);
        }
    }

    private void setLocale(String lang) {
        if (lang.equals(currentLang)) {
            // Language is already set, no need to set again
            return;
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("language", lang);
        editor.apply();
        currentLang = lang;

    }

    public void loadLocale(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = prefs.getString("language", "");
        setLocale(lang);
    }

    private String getStoredPasscode() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String passcode = sharedPreferences.getString("passcode", "");
        Log.d("PasscodeInput", "Stored passcode: " + passcode);
        return passcode;
    }
}
