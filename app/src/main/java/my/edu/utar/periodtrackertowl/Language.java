package my.edu.utar.periodtrackertowl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.service.voice.VoiceInteractionSession;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Language extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView textView;
    String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_language);

        radioGroup = findViewById(R.id.radio_group);
        textView = findViewById(R.id.tv_selected);

        // Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);

        ImageView imageView = findViewById(R.id.toolbar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Button buttonApply = findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);

                String lang = "";
                if (radioButton.getText().equals("English")|| radioButton.getText().equals("Bahasa Inggeris")||radioButton.getText().equals("英文")) {
                    // English
                    lang = "en";
                } else if (radioButton.getText().equals("Chinese")|| radioButton.getText().equals("中文")|| radioButton.getText().equals("Bahasa Cina")) {
                    // Chinese
                    lang = "zh";
                } else if (radioButton.getText().equals("Malay")|| radioButton.getText().equals("Bahasa Melayu")|| radioButton.getText().equals("马来语")) {
                    // Malay
                    lang = "ms";
                }

                if (!lang.equals(currentLang)) {
                    setLocale(lang);
                    recreate();
                }
            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);

        String selectedLanguage = getResources().getString(R.string.selected_language);
        String toastText = selectedLanguage + " " + radioButton.getText();

        Toast.makeText(Language.this,toastText, Toast.LENGTH_SHORT).show();
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
}
