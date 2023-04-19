package my.edu.utar.periodtrackertowl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class Background extends AppCompatActivity implements View.OnClickListener {
    String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);


        TextView brownTextView = findViewById(R.id.brown);
        brownTextView.setOnClickListener(this);

        TextView blueTextView = findViewById(R.id.crystal);
        blueTextView.setOnClickListener(this);

        TextView greenTextView = findViewById(R.id.soap);
        greenTextView.setOnClickListener(this);

        TextView cornsilkTextView = findViewById(R.id.water);
        cornsilkTextView.setOnClickListener(this);

        // Retrieve the saved color from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        ImageView imageView = findViewById(R.id.toolbar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Set the background color
        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);
        loadLocale();
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

    @Override
    public void onClick(View view) {
        int color = 0;
        switch(view.getId()) {
            case R.id.brown:
                color = Color.parseColor("#BC97895D");
                break;
            case R.id.crystal:
                color = Color.parseColor("#ACDDDE");
                break;
            case R.id.soap:
                color = Color.parseColor("#D6CDEA");
                break;
            case R.id.water:
                color = Color.parseColor("#DFF2FD");
                break;
        }

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("bg_color", color);
        editor.apply();

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);
    }

}