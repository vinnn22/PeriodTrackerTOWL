package my.edu.utar.periodtrackertowl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Locale;

public class signup extends AppCompatActivity {

    EditText signusername,signpassword,signre_password;
    TextView signlogin;
    Button signupButton;
    FirebaseDatabase db;
    DatabaseReference reference;
    String currentLang;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);

        signusername = findViewById(R.id.username);
        signpassword = findViewById(R.id.password);
        signre_password = findViewById(R.id.repassword);
        signlogin = findViewById(R.id.login);
        signupButton = findViewById(R.id.signup);

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

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Users");

                final String name = signusername.getText().toString();
                final String password = signpassword.getText().toString();
                String re_password = signre_password.getText().toString();

                if (!password.equals(re_password)) {
                    Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the username already exists in the database
                reference.orderByChild("username").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The username already exists
                            Toast.makeText(signup.this, "This username already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            // The username is available, create a new user in the database
                            helper helper = new helper(name, password, re_password);
                            reference.child(name).setValue(helper);
                            Toast.makeText(signup.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signup.this, login.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", databaseError.getMessage());
                    }
                });
            }
        });



        signlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
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

}

