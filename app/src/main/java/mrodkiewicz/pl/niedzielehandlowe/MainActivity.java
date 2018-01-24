package mrodkiewicz.pl.niedzielehandlowe;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.danlew.android.joda.JodaTimeAndroid;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;

import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import mrodkiewicz.pl.niedzielehandlowe.helpers.Data;
import mrodkiewicz.pl.niedzielehandlowe.tools.CloseSundayDecorator;
import mrodkiewicz.pl.niedzielehandlowe.tools.OpenWeekendDecorator;
import mrodkiewicz.pl.niedzielehandlowe.tools.TodayDecorator;



public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName()+ " flag";
    public static Data data = new Data();
    private MaterialCalendarView calendarView;
    private TextView textView;
    private ImageView imageView;
    private LinearLayout linearLayout;
    private int openSundayColor,closeSundayColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        openSundayColor = getResources().getColor(R.color.openSunday);
        closeSundayColor = getResources().getColor(R.color.closeSunday);

        ArrayList<CalendarDay> closeSundaysCollection = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (LocalDate localDate:data.dateCloseList){
            calendar.set(localDate.getYear(),localDate.getMonthOfYear() - 1,localDate.getDayOfMonth());
            CalendarDay day = CalendarDay.from(calendar);
            closeSundaysCollection.add(day);
            Log.d(TAG,day.toString());
        }


        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2018, 0, 1))
                .setMaximumDate(CalendarDay.from(2018, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        calendarView.addDecorators(
                new OpenWeekendDecorator(),
                new CloseSundayDecorator(closeSundayColor, closeSundaysCollection),
                new TodayDecorator()
        );

        if (data.isNextSundayClose()){
            textView.setText(getString(R.string.state_close_text));
            textView.setTextColor(closeSundayColor);
            imageView.setImageResource(R.drawable.ic_remove_shopping_cart_black_24dp);
            linearLayout.setBackgroundColor(closeSundayColor);

        }else{
            textView.setText(getString(R.string.state_open_text));
            textView.setTextColor(openSundayColor);
            imageView.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
            linearLayout.setBackgroundColor(openSundayColor);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
    public static Context getContext() {
        return getContext();
    }

    public static Data getData() {
        return data;
    }
}
