package pl.krakow.politechnika.io.wnuk.calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar toolbar2;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private static final int REQUEST_CODE_GET_EVENT = 1014;
    private CompactCalendarView compactCalendarView;
    private Button showPreviousMonthButton;
    private Button showNextMonthButton;
    private ListView eventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> mutableEvents = new ArrayList<>();
        eventsListView = (ListView) findViewById(R.id.lvEvents);
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mutableEvents);
        eventsListView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        showPreviousMonthButton = (Button) findViewById(R.id.bLeft);
        showNextMonthButton = (Button) findViewById(R.id.bRight);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        String myDate = "2018/12/16 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        Event ev1 = new Event(Color.RED, millis, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        Event ev2 = new Event(Color.GREEN, 1433704251000L);
        compactCalendarView.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendarView.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        showNextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollRight();
            }
        });

        showPreviousMonthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                compactCalendarView.scrollLeft();
            }
        });
    }


    /*
    Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Menu behaviour
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddEventActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_GET_EVENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Get message from intent
     * @param requestCode sent code to intent
     * @param resultCode sent code from intent
     * @param data da
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_GET_EVENT:
                if(resultCode == Activity.RESULT_OK){
                    // Get message
                    assert data != null;
                    String message = data.getStringExtra("message");
                } else{
                    Log.i("My app", "Activity canceled");
                }
        }
    }
}
