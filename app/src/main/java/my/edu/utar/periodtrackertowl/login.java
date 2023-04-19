package my.edu.utar.periodtrackertowl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

public class login extends AppCompatActivity {

    EditText logusername,logpassword;
    TextView logsignup;
    Button logButton;
    FirebaseDatabase db;
    DatabaseReference reference;
    String currentLang;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);

        logusername = findViewById(R.id.username);
        logpassword = findViewById(R.id.password);
        logsignup = findViewById(R.id.signup);
        logButton = findViewById(R.id.login);

// Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);

        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    builder.setTitle("Invalid Credentials");
                    builder.setMessage("Please enter a valid username and password");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    checkUser();
                }
            }
        });


        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,signup.class);
                startActivity(intent);
            }
        });
        loadLocale();
    }

    public Boolean validateUsername(){
        String val = logusername.getText().toString();
        if (val.isEmpty()){
            logusername.setError("Username cannot be empty");
            return false;
        }else{
            logusername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = logpassword.getText().toString();
        if (val.isEmpty()){
            logpassword.setError("Password cannot be empty");
            return false;
        }else{
            logpassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = logusername.getText().toString().trim();
        String userPassword = logpassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()){
                    logusername.setError(null);
                    String passwordFormD = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (!Objects.equals(passwordFormD, userPassword)){
                        logpassword.setError("Invalid Credentials");
                        logpassword.requestFocus();
                    }else{
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                    }

                }else{
                    logusername.setError("User not exist");
                    logusername.requestFocus();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
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
