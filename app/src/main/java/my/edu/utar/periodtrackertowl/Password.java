package my.edu.utar.periodtrackertowl;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Password extends AppCompatActivity {

    private Switch passcodeSwitch;
    private boolean isChecked;
    String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

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

        passcodeSwitch = findViewById(R.id.passcode_switch);
        isChecked = getPasscodeEnabled();
        passcodeSwitch.setChecked(isChecked);
        passcodeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheckedLocal = passcodeSwitch.isChecked();
                setPasscodeEnabled(isCheckedLocal);
                if (isCheckedLocal) {
                    showPasscodeDialog();
                } else {
                    confirmPasscodeDeactivation();
                }
            }
        });

        passcodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switchOn", isChecked);
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
            Configuration config = new Configuration();
            config.setLocale(newLocale);
            Context localizedContext = context.createConfigurationContext(config);
            Resources resources = localizedContext.getResources();
            Resources originalResources = context.getResources();
            return new ContextWrapper(localizedContext) {
                @Override
                public Resources getResources() {
                    return new ResourcesWrapper(originalResources, resources);
                }
            };
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


    private void confirmPasscodeDeactivation() {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_passcode))
                .setMessage(getString(R.string.enter_passcode))
                .setView(input)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String passcode = input.getText().toString();
                        if (passcode.equals(getStoredPasscode())) {
                            setPasscodeEnabled(false);
                            passcodeSwitch.setChecked(false);
                            Toast.makeText(Password.this, getString(R.string.passcode_turned_off), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Password.this, getString(R.string.incorrect_passcode), Toast.LENGTH_SHORT).show();
                            passcodeSwitch.setChecked(true);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }


    private boolean getPasscodeEnabled() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("passcodeEnabled", false);
    }

    private void setPasscodeEnabled(boolean enabled) {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("passcodeEnabled", enabled);
        editor.apply();
    }

    private void showPasscodeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.set_passcode));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String passcode = input.getText().toString();
                if (passcode.length() == 4) {
                    setPasscode(passcode);
                    Toast.makeText(Password.this, getString(R.string.passcode_set_successfully), Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(Password.this);
                    errorBuilder.setTitle(getString(R.string.error));
                    errorBuilder.setMessage(getString(R.string.passcode_must_be_four_digits));
                    errorBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    errorBuilder.show();
                    passcodeSwitch.setChecked(false);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passcodeSwitch.setChecked(false);
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (passcodeSwitch.isChecked()) {
                    Intent intent = new Intent(Password.this, PasscodeInput.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        builder.show();
    }

    private void setPasscode(String passcode) {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("passcode", passcode);
        editor.apply();
    }

    private String getStoredPasscode() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("passcode", "");
    }

}
