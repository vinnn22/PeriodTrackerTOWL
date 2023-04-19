package my.edu.utar.periodtrackertowl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomCalendarView extends CalendarView {


    public static final int NUM_COLUMNS = 7;
    private List<Date> highlightedDates;

    public static final int NUM_ROWS = 6;

    TextView[][] calendarTextViews;

    public CustomCalendarView(Context context) {
        super(context);
        init();
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        highlightedDates = new ArrayList<>();
        calendarTextViews = new TextView[NUM_ROWS][NUM_COLUMNS];
    }

    public void setHighlightedDates(List<Date> dates) {
        highlightedDates = dates;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Paint pinkPaint = new Paint();
        pinkPaint.setColor(Color.parseColor("#FFC0CB")); // pink color
        pinkPaint.setStyle(Paint.Style.FILL);

        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.parseColor("#7FFF00")); // green color
        greenPaint.setStyle(Paint.Style.FILL);

        Calendar calendar = Calendar.getInstance();
        for (Date date : highlightedDates) {
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            int x = (int) (width / 7.0f * (day - 1) + width / 7.0f / 2);
            int y = (int) (height / 6.0f * (month + 1) - height / 6.0f / 2);

            // Check if the current date is the next period start date
            if (isNextPeriodStartDate(calendar)) {
                canvas.drawCircle(x, y, height / 14, pinkPaint);
            }
            // Check if the current date is the last period date
            else if (isLastPeriodDate(calendar)) {
                canvas.drawCircle(x, y, height / 14, greenPaint);
            }
        }
    }

    private boolean isNextPeriodStartDate(Calendar calendar) {
        // Replace the logic below with your own logic to determine if a date is the next period start date
        Calendar nextPeriodStartDate = Calendar.getInstance();
        nextPeriodStartDate.add(Calendar.DAY_OF_MONTH, 28); // assuming next period start date is 28 days after last period start date
        return calendar.get(Calendar.YEAR) == nextPeriodStartDate.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == nextPeriodStartDate.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == nextPeriodStartDate.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isLastPeriodDate(Calendar calendar) {
        // Replace the logic below with your own logic to determine if a date is the last period date
        Calendar lastPeriodDate = Calendar.getInstance();
        lastPeriodDate.add(Calendar.DAY_OF_MONTH, -5); // assuming last period ended 5 days ago
        return calendar.get(Calendar.YEAR) == lastPeriodDate.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == lastPeriodDate.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == lastPeriodDate.get(Calendar.DAY_OF_MONTH);
    }

    private TextView findTextView(int dayOfMonth, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int row = (calendar.get(Calendar.WEEK_OF_MONTH) - 1);
        int column = dayOfWeek - 1;

        return calendarTextViews[row][column];
    }


}
