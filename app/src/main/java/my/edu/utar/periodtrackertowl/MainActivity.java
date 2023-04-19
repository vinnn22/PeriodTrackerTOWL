package my.edu.utar.periodtrackertowl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static final String PREFS_PASSCODE = "prefs_passcode";
    private Switch passcodeSwitch;
    private String passcode;
    String currentLang;
    private CalendarView calendarView;
    private Date nextPeriodDate;
    private Date Date;
    private String selectedDate;
    private DatabaseReference databaseReference;
    private Dialog dialog;
    private Button cmrbutton;
    private ImageView imageview;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int counter = 0;
    private long lastClickTime = 0;
    private ImageView lastClickedImageView;
    private String userSelection;
    private static final int REQUEST_CODE = 100;
    private ImageView image;
    private Bitmap photo;
    private StorageReference mStorageRef;
    private String event;
    private String Sexual_activity;
    private String Moods;
    private String Pain_rate;
    private String Flow;
    private String photoUrl;
    private DatabaseReference reference;
    public String w, x, y, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);

        showPeriodDateDialog();
        calendarView = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarView.setDate(System.currentTimeMillis());

        // Get the saved last period date from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        int lastPeriodDay = preferences.getInt("lastPeriodDay", 0);
        int lastPeriodMonth = preferences.getInt("lastPeriodMonth", 0);
        int lastPeriodYear = preferences.getInt("lastPeriodYear", 0);

        //Calculate the last period date
        Calendar lastPeriodDate = Calendar.getInstance();
        lastPeriodDate.set(lastPeriodYear, lastPeriodMonth, lastPeriodDay);


        // Calculate the next period date
        Calendar nextPeriodDate = Calendar.getInstance();
        nextPeriodDate.set(lastPeriodYear, lastPeriodMonth, lastPeriodDay);
        nextPeriodDate.add(Calendar.DAY_OF_MONTH, 28); // Add 28 days to calculate next period date

        int lastPeriodColor = Color.RED;
        int nextPeriodColor = Color.GREEN;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                long clickTime = System.currentTimeMillis();

                //click twice to come out dialog
                if (clickTime - lastClickTime > 500) {
                    counter = 1;
                } else {
                    counter++;
                }

                lastClickTime = clickTime;

                if (counter == 2) {
                    counter = 0;
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);
                    Date selectedDate = selectedCalendar.getTime();
                    showAddEventDialog(selectedDate);
                }

            }
        });


        TextView tvCalendar = findViewById(R.id.calendar);
        tvCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Report.class);
                startActivity(intent);
            }
        });

        TextView setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });

        TextView pregnancy = findViewById(R.id.pregnancy);
        pregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pregnancy.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
        mStorageRef = FirebaseStorage.getInstance().getReference();

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

    public void loadLocale() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = prefs.getString("language", "");
        setLocale(lang);
    }

    // Add a function to let the user choose the last clicked ImageView
    private void chooseLastClickedImageView1(ImageView imageView, ImageView imageView2, ImageView tickImageView, ImageView tickImageView2) {
        if (lastClickedImageView != null) {
            if (lastClickedImageView == imageView) {
                tickImageView.setVisibility(View.VISIBLE);
                tickImageView2.setVisibility(View.INVISIBLE);
                w = "Protected";

            } else if (lastClickedImageView == imageView2) {
                tickImageView.setVisibility(View.INVISIBLE);
                tickImageView2.setVisibility(View.VISIBLE);
                w = "Unprotected";

            }
        }
    }

    private void chooseLastClickedImageView2(ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView tickImageView3, ImageView tickImageView4, ImageView tickImageView5) {
        if (lastClickedImageView != null) {
            if (lastClickedImageView == imageView3) {
                tickImageView3.setVisibility(View.VISIBLE);
                tickImageView4.setVisibility(View.INVISIBLE);
                tickImageView5.setVisibility(View.INVISIBLE);
                x = "Happy";
            } else if (lastClickedImageView == imageView4) {
                tickImageView3.setVisibility(View.INVISIBLE);
                tickImageView4.setVisibility(View.VISIBLE);
                tickImageView5.setVisibility(View.INVISIBLE);
                x = "Annoy";
            } else if (lastClickedImageView == imageView5) {
                tickImageView3.setVisibility(View.INVISIBLE);
                tickImageView4.setVisibility(View.INVISIBLE);
                tickImageView5.setVisibility(View.VISIBLE);
                x = "Sad";
            }
        }
    }

    private void chooseLastClickedImageView3(ImageView imageView6, ImageView imageView7, ImageView imageView8, ImageView tickImageView6, ImageView tickImageView7, ImageView tickImageView8) {
        if (lastClickedImageView != null) {
            if (lastClickedImageView == imageView6) {
                tickImageView6.setVisibility(View.VISIBLE);
                tickImageView7.setVisibility(View.INVISIBLE);
                tickImageView8.setVisibility(View.INVISIBLE);
                y = "pain x 1";
            } else if (lastClickedImageView == imageView7) {
                tickImageView6.setVisibility(View.INVISIBLE);
                tickImageView7.setVisibility(View.VISIBLE);
                tickImageView8.setVisibility(View.INVISIBLE);
                y = "pain x 2";
            } else if (lastClickedImageView == imageView8) {
                tickImageView6.setVisibility(View.INVISIBLE);
                tickImageView7.setVisibility(View.INVISIBLE);
                tickImageView8.setVisibility(View.VISIBLE);
                y = "pain x 3";
            }
        }
    }

    private void chooseLastClickedImageView4(ImageView imageView9, ImageView imageView10, ImageView imageView11, ImageView tickImageView9, ImageView tickImageView10, ImageView tickImageView11) {
        if (lastClickedImageView != null) {
            if (lastClickedImageView == imageView9) {
                tickImageView9.setVisibility(View.VISIBLE);
                tickImageView10.setVisibility(View.INVISIBLE);
                tickImageView11.setVisibility(View.INVISIBLE);
                z = "Less";
            } else if (lastClickedImageView == imageView10) {
                tickImageView9.setVisibility(View.INVISIBLE);
                tickImageView10.setVisibility(View.VISIBLE);
                tickImageView11.setVisibility(View.INVISIBLE);
                z = "Medium";
            } else if (lastClickedImageView == imageView11) {
                tickImageView9.setVisibility(View.INVISIBLE);
                tickImageView10.setVisibility(View.INVISIBLE);
                tickImageView11.setVisibility(View.VISIBLE);
                z = "Lot";
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);


            // Upload the photo to Firebase Storage
            StorageReference photoRef = mStorageRef.child("images/" + UUID.randomUUID().toString() + ".jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dt = baos.toByteArray();
            UploadTask uploadTask = photoRef.putBytes(dt);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded photo
                    photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photoUrl = uri.toString();

                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                            databaseRef.child("photos").child(UUID.randomUUID().toString()).setValue(photoUrl);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to upload photo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showAddEventDialog(Date date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_detail, null);
        builder.setView(dialogLayout);

        TextView titleTextView = dialogLayout.findViewById(R.id.title_text_view);
        TextView eventDescriptionTextView = dialogLayout.findViewById(R.id.event_description);

        cmrbutton = dialogLayout.findViewById(R.id.btncamera);
        image = dialogLayout.findViewById(R.id.imageView);

        cmrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "No camera app found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView imageView = dialogLayout.findViewById(R.id.event_image_view);
        ImageView tickImageView = dialogLayout.findViewById(R.id.tickImageView);
        ImageView imageView2 = dialogLayout.findViewById(R.id.event_image_view2);
        ImageView tickImageView2 = dialogLayout.findViewById(R.id.tickImageView2);

        ImageView imageView3 = dialogLayout.findViewById(R.id.event_image_view3);
        ImageView tickImageView3 = dialogLayout.findViewById(R.id.tickImageView3);
        ImageView imageView4 = dialogLayout.findViewById(R.id.event_image_view4);
        ImageView tickImageView4 = dialogLayout.findViewById(R.id.tickImageView4);
        ImageView imageView5 = dialogLayout.findViewById(R.id.event_image_view5);
        ImageView tickImageView5 = dialogLayout.findViewById(R.id.tickImageView5);

        ImageView imageView6 = dialogLayout.findViewById(R.id.event_image_view6);
        ImageView tickImageView6 = dialogLayout.findViewById(R.id.tickImageView6);
        ImageView imageView7 = dialogLayout.findViewById(R.id.event_image_view7);
        ImageView tickImageView7 = dialogLayout.findViewById(R.id.tickImageView7);
        ImageView imageView8 = dialogLayout.findViewById(R.id.event_image_view8);
        ImageView tickImageView8 = dialogLayout.findViewById(R.id.tickImageView8);

        ImageView imageView9 = dialogLayout.findViewById(R.id.event_image_view9);
        ImageView tickImageView9 = dialogLayout.findViewById(R.id.tickImageView9);
        ImageView imageView10 = dialogLayout.findViewById(R.id.event_image_view10);
        ImageView tickImageView10 = dialogLayout.findViewById(R.id.tickImageView10);
        ImageView imageView11 = dialogLayout.findViewById(R.id.event_image_view11);
        ImageView tickImageView11 = dialogLayout.findViewById(R.id.tickImageView11);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView; // Set lastClickedImageView to imageView
                chooseLastClickedImageView1(imageView, imageView2, tickImageView, tickImageView2);

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView2; // Set lastClickedImageView to imageView2
                chooseLastClickedImageView1(imageView, imageView2, tickImageView, tickImageView2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView3; // Set lastClickedImageView to imageView
                chooseLastClickedImageView2(imageView3, imageView4, imageView5, tickImageView3, tickImageView4, tickImageView5);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView4; // Set lastClickedImageView to imageView2
                chooseLastClickedImageView2(imageView3, imageView4, imageView5, tickImageView3, tickImageView4, tickImageView5);
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView5; // Set lastClickedImageView to imageView
                chooseLastClickedImageView2(imageView3, imageView4, imageView5, tickImageView3, tickImageView4, tickImageView5);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView6; // Set lastClickedImageView to imageView
                chooseLastClickedImageView3(imageView6, imageView7, imageView8, tickImageView6, tickImageView7, tickImageView8);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView7; // Set lastClickedImageView to imageView2
                chooseLastClickedImageView3(imageView6, imageView7, imageView8, tickImageView6, tickImageView7, tickImageView8);
            }
        });
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView8; // Set lastClickedImageView to imageView
                chooseLastClickedImageView3(imageView6, imageView7, imageView8, tickImageView6, tickImageView7, tickImageView8);
            }
        });
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView9; // Set lastClickedImageView to imageView
                chooseLastClickedImageView4(imageView9, imageView10, imageView11, tickImageView9, tickImageView10, tickImageView11);
            }
        });
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView10; // Set lastClickedImageView to imageView2
                chooseLastClickedImageView4(imageView9, imageView10, imageView11, tickImageView9, tickImageView10, tickImageView11);
            }
        });
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add code to allow user to select image
                lastClickedImageView = imageView11; // Set lastClickedImageView to imageView
                chooseLastClickedImageView4(imageView9, imageView10, imageView11, tickImageView9, tickImageView10, tickImageView11);
            }
        });

        final EditText eventEditText = dialogLayout.findViewById(R.id.event_edit_text);


        Button saveButton = dialogLayout.findViewById(R.id.dialog_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = eventEditText.getText().toString().trim();

                String Sexual_activity = w;
                String Moods = x;
                String Pain_rate = y;
                String Flow = z;
                String photoUrl=null;

                if (!TextUtils.isEmpty(event)) {
                    saveEventToDatabase(date, Sexual_activity, Moods, Pain_rate, Flow, event, photoUrl); // Pass the photoUrl variable to the method
                    Toast.makeText(MainActivity.this, "Event saved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog = builder.create();
        dialog.show();

    }
        private void showPeriodDateDialog() {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_period_date);

            final DatePicker datePicker = dialog.findViewById(R.id.datePicker);

            Button submitButton = dialog.findViewById(R.id.submitButton);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the selected date from the date picker
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();

                    // Save the date
                    SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("lastPeriodDay", day);
                    editor.putInt("lastPeriodMonth", month);
                    editor.putInt("lastPeriodYear", year);
                    editor.apply();

                    // Calculate the next period date and show it to the user
                    Calendar lastPeriodDate = Calendar.getInstance();
                    lastPeriodDate.set(year, month, day);
                    lastPeriodDate.add(Calendar.DAY_OF_MONTH, -5); // Add 28 days to calculate next period date

                    Calendar nextPeriodDate = Calendar.getInstance();
                    nextPeriodDate.setTimeInMillis(lastPeriodDate.getTimeInMillis());
                    nextPeriodDate.add(Calendar.DAY_OF_MONTH, 28);

                    Calendar endPeriodDate = Calendar.getInstance();
                    endPeriodDate.setTimeInMillis(lastPeriodDate.getTimeInMillis());
                    endPeriodDate.add(Calendar.DAY_OF_MONTH,35);

                    // Dismiss the dialog
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String nextDate = sdf.format(nextPeriodDate.getTime());
                    String endDate = sdf.format(endPeriodDate.getTime());
                    Toast.makeText(MainActivity.this, "Your next period date is " + nextDate +"\n"+ "Your end period date is " + endDate, Toast.LENGTH_LONG).show();                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    private void saveEventToDatabase(Date selectedDate, String Sexual_activity, String Moods, String Pain_rate, String Flow, String event,String photoUrl) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");

        // Convert the selectedDate to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(selectedDate);
        DatabaseReference newEventRef = eventsRef.child(dateStr);
        // Create a new event object
        String eventId = eventsRef.push().getKey();
        Event newEvent = new Event(selectedDate, Sexual_activity, Moods, Pain_rate, Flow, event,photoUrl);


        // save the event to the database
        newEventRef.setValue(newEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to save event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onDateSelected(Date date) {
        showAddEventDialog(date);
    }
}