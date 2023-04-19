package my.edu.utar.periodtrackertowl;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

public class faq extends AppCompatActivity {

    String currentLang;

    private QuestionAnswer[] questionAnswers = {
            new QuestionAnswer("What is this app about?",
                    "\nThis app is a period tracker that allows users to record details during " +
                            "their menstrual cycle.\n" +
                            "The app will help you to approximate next period time, evolution period, and so on. \n"),



            new QuestionAnswer("How to use this app?",
                    "\nUsers can select a data on the calender and add the event details, " +
                            "such as sexual activity, moods, pain rate, flow, and so on.\n" +
                            "You can also review the report in order to more understand you menstrual cycle. \n"),




            new QuestionAnswer("Can I get pregnant during my period?",
                    "\nAccording to Nhs.uk (2018), yes, but not very likely.However if you without using any contraception" +
                            "when having sex, you may get pregnant during or after your menstrual cycle.\n" +
                            "\n\n\n\nReference:\n" +
                            "Nhs.uk. " +
                            "https://www.nhs.uk/common-health-questions/pregnancy/can-i-get-pregnant-just-after-my-period-has-finished/\n"),



            new QuestionAnswer("The most likely time to get pregnant",
                    "\nAccording to Nhs.uk (2018),the time of ovulation, occurs 12 to 14 days before your next period starts.\n" +
                            "That time is the period that your easily to pregnant.\n" +
                            "After you have sex without contraception sperm sometimes can service at most 3-5 days in your body." +
                            "\nIf your ovulation period early, you have high risk to pregnant. \n" +
                            "\n\n\n\nReference:\n" +
                            "Nhs.uk. " +
                            "https://www.nhs.uk/common-health-questions/pregnancy/can-i-get-pregnant-just-after-my-period-has-finished/\n"),



            new QuestionAnswer("What contraception can prevent pregnancy",
                    "\n1.Condom \nPrevent sperm entering the uterus. " +
                            "\n\n2.Oral Contraceptive Pill \nHormonal method that thickens cervical mucus to prevent sperm from reaching the egg" +
                            "\n\n3.Intrauterine Device (IUD) \nSmall T-shaped device that is inserted into the uterus to prevent fertilization by interfering with sperm"  +
                            "\n\n4.Contraceptive Implant \nSmall rod inserted under the skin of the upper arm and releases hormones to prevent ovulation." +
                            "\n\n5.Contraceptive Injection \nHormonal method of contraception that is injected into the body every few months to prevent ovulation." +
                            "\n\n6.‘Morning After’ Pill \nEmergency contraception that can prevent pregnancy after unprotected sex " +
                            "\n\n7.Contraceptive Ring \nHormonal method of contraception that is inserted into the vagina and releases hormones to prevent ovulation." +
                            "\n\n8.Diaphragm \nbarrier method of contraception that is placed over the cervix to prevent sperm from entering the uterus during sexual intercourse." +
                            "\n\n9.Sterilisation \nInvolves surgically blocking or cutting the fallopian tubes in women or the vas deferens in men to prevent the eggs and sperm from meeting.\n "
                            +"\n\n\n\nReference:\n" +
                            "9 types of contraception you can use to prevent pregnancy | Stop the rise. (n.d.)." +
                            " Stoptherise.initiatives.qld.gov.au. https://stoptherise.initiatives.qld.gov.au/blog/9-contraception-options\n"),



            new QuestionAnswer("What is evaluation?",
                    "\nOvulation is the process about which an egg (ovum) is released from one of the ovaries. " +
                            "\nThis will occurs once per menstrual cycle, when a surge in utilizing hormone (LH) it will which causes the matured egg to be released from the ovary. " +
                            "\nThe egg will travel from the fallopian tube toward the uterus. " +
                            "\nIf you have sex without contraception during this time, there is a high chance that the egg will be fertilized by sperm, which will cause pregnancy. " +
                            "\n\n\n\nReference:\n" +
                            "Services, D. of H. & H. (n.d.). Ovulation. " +
                            "Www.betterhealth.vic.gov.au. https://www.betterhealth.vic.gov.au/health/conditionsandtreatments/ovulation\n"),




            new QuestionAnswer("What symptoms will I face before period?",
                    "\nSome of the female will have the symptoms like: \n\n" +
                            "-feeling bloated\n" +
                            "-breast tenderness\n" +
                            "-mood swings\n" +
                            "-feeling irritable\n" +
                            "-spotty skin\n" +
                            "-low sex drive (loss of libido)" +
                            "\n\n\n\nReference:\n" +
                            "NHS Choices. (2019). Overview - Periods. NHS. https://www.nhs.uk/conditions/periods/\n"),



            new QuestionAnswer("What should I do if I period pain?",
                    "\nIf you are facing a period pain problem, you can:" +
                            "\n\n-Use heat pad or soaking in a warm bath can help to release pain." +
                            "\n-Take a pain reliever." +
                            "\n-Exercises can stimulate the release of endorphin in order to block the pain." +
                            "\n-Reduce your stress." +
                            "\n-Get vitamins and minerals." +
                            "\n\n\n\nReference:\n" +
                            "5 Tips for Getting Relief From Period Cramps & Pain. (n.d.). " +
                            "Www.houstonmethodist.org. https://www.houstonmethodist.org/blog/articles/2021/sep/menstrual-cramps-5-tips-for-getting-relief-from-period-pain/\n"),



            new QuestionAnswer("What should not do when during period?",
                    "\nThe things can cause your worsen menstrual cramps, lead to infections and discomfort, " +
                            "hence you should prevent do when during period:" +
                            "\n\n-Eat too much salty food" +
                            "\n-Drinking too much coffee" +
                            "\n-Using a douche" +
                            "\n-Wearing the same sanitary product for more than 4-6 hours" +
                            "\n-Waxing or shaving" +
                            "\n-Having unprotected sex" +
                            "\n-Smoking" +
                            "\n-Going to bed without a pad" +
                            "\n-Getting a breast exam during menstruation" +
                            "\n-Skipping meals " +
                            "\n-Eating junk food. " +
                            "\n\n\n\nReference:\n" +
                            "Your periods can be extremely uncomfortable if you don’t avoid these 10 things. (2021, April 16). " +
                            "Healthshots. https://www.healthshots.com/intimate-health/menstruation/10-things-you-shouldnt-do-on-your-periods/\n"),



            new QuestionAnswer("How often should I change my sanitary pad?",
                    "\nNormally you need to change your sanitary pad every 3 or 4 hours. " +
                            "This can keep you in good hygiene and prevent the bacteria grow up. " +
                            "Changing a pad more can help you to prevent leak and bad odors. " +
                            "\n\n\n\nReference:\n" +
                            "Can I Wear the Same Pad All Day? (for Teens) - Nemours KidsHealth. (n.d.). " +
                            "Kidshealth.org. https://kidshealth.org/en/teens/changing-pads.html\n"),



            new QuestionAnswer("How long the menstrual cycle?",
                    "\nMenstrual cycle is calculated from the first day of your period until the next start of your next period.\n" +
                            "The normal average of a menstrual cycle for most women are 28 days." +
                            "However, there are also some of the women's menstrual cycles to be shorter, 23 days, or longer, up to 35 days." +
                            "\n\n\n\nReference:\n" +
                            "NHS Choices. (2019, August 5). Periods and fertility in the menstrual cycle - Periods. " +
                            "NHS. https://www.nhs.uk/conditions/periods/fertility-in-the-menstrual-cycle/\n"),



            new QuestionAnswer("Do you understand your body?",
                    "\nYou must understand your body in order to more understand the menstrual cycle.\n" +
                            "\n" +
                            "1. Every woman has two ovaries. Ovaries are the places where eggs can be stored, developed, and released. Both ovaries will typically alternate in releasing eggs. \n" +
                            "For example, in one menstrual cycle, an egg may be released by the left ovary, while in the next menstrual cycle, an egg may be released by the right ovary.\n" +
                            "\n" +
                            "2. The womb which also known as the uterus, is the place where fertilized eggs implant and develop.\n" +
                            "\n" +
                            "3. The fallopian tubes are the two thin tubes that help to connect the ovaries to the uterus. Eggs will travel from the ovaries to the uterus via these tubes, and it is also the place where the egg can be fertilized by sperm.\n" +
                            "\n" +
                            "4. The cervix is a narrow part of the uterus that connects the uterus to the vagina. It opens up during childbirth and menstruation." +
                            "\n\n\n\nReference:\n" +
                            "NHS Choices. (2019, August 5). Periods and fertility in the menstrual cycle - Periods. " +
                            "NHS. https://www.nhs.uk/conditions/periods/fertility-in-the-menstrual-cycle/\n"),



            new QuestionAnswer("The color of period blood.",
                    "\nDo you know that different color of blood period represent different health conditions?" +
                            "\n\n\nBlack" +
                            "\nThe black color of blood basically will appear at the beginning or end of the period. " +
                            "The blood will be black due to old blood or blood that longer stays in the uterus and cause the blood to change from color brown to dark red, and finally to black." +
                            "\nDue to the black color of the blood staying a long time in the uterus, hence the woman may have a blockage inside the vagina." +
                            "\n\n\nBrown or dark red" +
                            "\nThe brown or dark red color of blood is similar to black blood, in which the blood stays a long time in the uterus and has oxidized. But the time oxidize or stays in the uterus is no longer as the black blood.\n" +
                            "The other conditions that will appear as brown and dark red blood, maybe a sign of pregnancy, brown color blood also can be a sign of miscarriage. " +
                            "The second condition is it is a normal phenomenon in the uterus that expels excess blood and tissue after the women give birth." +
                            "The third condition is hormone level changes after giving birth may cause irregular periods." +
                            "\n\n\nBright red" +
                            "\nBright red blood refers to fresh blood and a steady flow. A period usually starts with bright red blood and ends with darker blood. It is normal for the blood color to remain bright red throughout the period.\n" +
                            "Unusual spotting or bleeding may be a sign of a sexually transmitted infection. In rare cases, bright red blood may be a sign of cervical cancer." +
                            "\n\n\nPink" +
                            "\nPink blood or spotting can occur when period blood mixes with cervical fluid, or due to hormonal birth control that lowers estrogen levels in the body. " +
                            "Pink blood can also result from small tears created in the vagina during sexual intercourse, as blood mixes with these tears and exits the body as pink discharge." +
                            "\n\n\nOrange" +
                            "\nMost of the orange color blood or discharge is usually a sign of an infection, such as bacterial vaginosis or trichinosis." +
                            "\n\n\nGrey" +
                            "\nThe grey discharge can be a sign of other conditions, such as cervical or vaginal infections.If your are during pregnancy, you may indicate a miscarriage. " +
                            "Therefore, it is important to seek immediate medical attention to determine the cause and receive proper treatment." +
                            "\n\n\n\nReference:\n" +
                            "Period blood chart: What does the blood color mean? (2019, April 1)." +
                            " Www.medicalnewstoday.com. https://www.medicalnewstoday.com/articles/324848#gray\n"),



            new QuestionAnswer("What is safe period?",
                    "\n" +
                            "Safe period is the period that women will not easily get pregnant. " +
                            "Your ovulation will occur around 14 days before next menstrual period. " +
                            "To be safe you need to consider a period of 10 days which includes the 5 days before and 5 days after ovulation." +
                            " All of the time outside this period are consider as a safe period." +
                            "\n\nThere are two parts of save period, which are safe period before ovulation and safe period after ovulation. " +
                            "The safe period before ovulation  from the day after your menstrual period ends and continues until the day before ovulation. " +
                            "The safe period after ovulation starts from the first day after your ovulation and continues until the day before the next menstrual period. " +
                            "The safe period after ovulation is considered safer than the safe period before ovulation." +
                            "\n\n\n\nReference:\n" +
                            "Safe Period: What is the Safe Period to Have Sex? | How to Calculate the Time of Ovulation? (2017, April 28)." +
                            " Www.icliniq.com. https://www.icliniq.com/articles/sexual-health/what-is-safe-period\n"),



            new QuestionAnswer("What is fertile period\\fertile window?",
                    "\n" +
                            "Every women will release one egg during their period, the egg can survive 12-24 hours after it released, " +
                            "while sperm can survive 3-5 days. The period of fertilization is mainly within 24 hours after ovulation. " +
                            "After 2-3 days, sperm lose their ability to combine with the egg. " +
                            "Therefore, sexual intercourse 2-3 days before ovulation and 1-2 days after ovulation may lead to pregnancy. " +
                            "\n\n\n\nReference:\n" +
                            "Safe Period: What is the Safe Period to Have Sex? | How to Calculate the Time of Ovulation? (2017, April 28)." +
                            " Www.icliniq.com. https://www.icliniq.com/articles/sexual-health/what-is-safe-period\n"),



            new QuestionAnswer("Why I always irregular periods?",
                    "\nIf your menstrual period length are not same and keep changing everytime, " +
                            "then your are consider as irregular periods. " +
                            "Your period may come late or early, but if you are in below condition you are common:" +
                            "\n\n" +
                            "-Puberty\n" +
                            "-Start of the menopause (age 45 - 55)\n" +
                            "-Early pregnancy\n" +
                            "-Hormonal contraception\n" +
                            "-Medical conditions\n" +
                            "-Extreme weight loss or weight gain, excessive exercise or stress\n" +
                            "\n\n" +
                            "If you are in below conditions, it's recommended to consult a doctor:\n" +
                            "\n" +
                            "-Your age under 45 and suddenly irregular period\n" +
                            "-Your menstrual period less than 21 days or more than 35 days\n" +
                            "-Your period longer than 7 days\n" +
                            "-You have a big difference (at least 20 days) between your shortest and longest menstrual cycle." +
                            "\n\n\n\nReference:\n" +
                            "Irregular periods. (2017, October 24). Nhs.uk. " +
                            "https://www.nhs.uk/conditions/irregular-periods/\n"),



            new QuestionAnswer("Why have my periods stopped and missed?",
                    "\nThere are various reasons why you may miss your period or why periods might stop altogether." +
                            "Some of the common reasons include pregnancy, stress, sudden weight loss, being overweight, doing too much exercise, taking the contraceptive pill, the menopause, and polycystic ovary syndrome (PCOS). " +
                            "Periods can also sometimes stop due to a medical condition, such as heart disease, uncontrolled diabetes, an overactive thyroid, or premature menopause." +
                            "\n\n\n\nReference:\n" +
                            "NHS. (2017, October 18). Stopped or missed periods. Nhs.uk. " +
                            "https://www.nhs.uk/conditions/stopped-or-missed-periods/\n"),
    };

    public void setFilterList(List<QuestionAnswer> filterList) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_layout);

// Retrieve the stored color from the shared preference
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int color = sharedPref.getInt("bg_color", Color.parseColor("#BC97895D"));

        LinearLayout layout = findViewById(R.id.layout);
        layout.setBackgroundColor(color);

        ScrollView scrollView = findViewById(R.id.scrollView);
        ImageView imageView = findViewById(R.id.toolbar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });






        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

            private void filterList(String text) {

            }
        });

        LinearLayout linearLayout = findViewById(R.id.linearlayout);


        for (int i = 0; i < questionAnswers.length; i++) {
            TextView questionTextView = new TextView(this);
            questionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            questionTextView.setText(questionAnswers[i].getQuestion());
            questionTextView.setBackgroundColor(getResources().getColor(R.color.question));
            questionTextView.setTextColor(getResources().getColor(R.color.mibai2));
            questionTextView.setPadding(16, 16, 16, 16);
            questionTextView.setTextSize(22);
            linearLayout.addView(questionTextView);

            TextView answerTextView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 16);
            answerTextView.setLayoutParams(params);
            answerTextView.setText(questionAnswers[i].getAnswer());
            answerTextView.setBackgroundColor(getResources().getColor(R.color.mibai));
            answerTextView.setTextColor(getResources().getColor(R.color.question2));
            answerTextView.setPadding(16, 16, 16, 16);
            answerTextView.setTextSize(18);
            linearLayout.addView(answerTextView);
        }

        for(int i = 0; i < questionAnswers.length; i++) {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    linearLayout.removeAllViews();
                    for (QuestionAnswer questionAnswer : questionAnswers) {
                        if (questionAnswer.getQuestion().toLowerCase(Locale.getDefault()).
                                contains(newText.toLowerCase(Locale.getDefault())) ||
                                questionAnswer.getAnswer().toLowerCase(Locale.getDefault()).
                                        contains(newText.toLowerCase(Locale.getDefault()))) {

                            TextView questionTextView = new TextView(faq.this);
                            questionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            questionTextView.setText(questionAnswer.getQuestion());
                            questionTextView.setBackgroundColor(getResources().getColor(R.color.question));
                            questionTextView.setTextColor(getResources().getColor(R.color.mibai2));
                            questionTextView.setPadding(16, 16, 16, 16);
                            questionTextView.setTextSize(22);
                            linearLayout.addView(questionTextView);

                            TextView answerTextView = new TextView(faq.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 0, 0, 16);
                            answerTextView.setLayoutParams(params);
                            answerTextView.setText(questionAnswer.getAnswer());
                            answerTextView.setBackgroundColor(getResources().getColor(R.color.mibai));
                            answerTextView.setTextColor(getResources().getColor(R.color.question2));
                            answerTextView.setPadding(16, 16, 16, 16);
                            answerTextView.setTextSize(18);
                            linearLayout.addView(answerTextView);
                        }
                    }
                    return false;
                }
            });

        }
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
