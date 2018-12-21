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

        showPreviousMonthButton = (Button) findViewById(R.id.bLeft);
        showNextMonthButton = (Button) findViewById(R.id.bRight);
        eventsListView = (ListView) findViewById(R.id.lvEvents);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        final ArrayList<Event> mutableEvents = new ArrayList<>();
        final ArrayAdapter adapter = new EventsAdapter(this, mutableEvents);

        eventsListView.setAdapter(adapter);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        /**
         * Calendar setup
         */
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> eventsList = compactCalendarView.getEvents(dateClicked);
                if (eventsList != null) {
                    mutableEvents.clear();
                    for (Event booking : eventsList) {
                        mutableEvents.add(booking);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        /**
         * Button to scroll to next or previous month
         */
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

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Menu behaviour
     * Opens activity add event
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddEventActivity.class);
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
                    String title = data.getStringExtra("title");
                    String date = data.getStringExtra("date");
                    String color = data.getStringExtra("color");
                    addEvent(title, date, color);
                } else{
                    Log.i("My app", "Activity canceled");
                }
        }
    }


    /**
     * Adding events
     * @param title
     * @param sDate
     * @param colorString
     */
    public void addEvent(String title, String sDate, String colorString){
        String myDate = sDate + " 10:00:00";
        int color = stringToColor(colorString);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();


        Event ev1 = new Event(color, millis, title +" at: " + sDate);
        compactCalendarView.addEvent(ev1);
    }

    public int stringToColor(String color){
        switch (color){
            case "GRAY":
                return Color.GRAY;
            case "YELLOW":
                return Color.YELLOW;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            case "RED":
                return Color.RED;
            case "WHITE":
                return Color.WHITE;
        }
        return Color.WHITE;
    }
}
