package pl.krakow.politechnika.io.wnuk.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String title;
    private String date;
    private String dateTextView;
    private EditText titleInput;
    private Button submitButton;
    private boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        /**
         * Toolbar added
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String titleToolbar = getResources().getString(R.string.add_activity);
        getSupportActionBar().setTitle(titleToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleInput = (EditText) findViewById(R.id.etTitle);
        submitButton = (Button) findViewById(R.id.bSubmit);


        /**
         * Return data from activity
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Extract data from UI
                title = titleInput.getText().toString();

                //Pass data back
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        /**
         * Date picker setup
         */
        mDisplayDate = (TextView) findViewById(R.id.tvDatePicker);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddEventActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                //if()
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                date = year + "/" + month + "/" + day;
                dateTextView = day + "." + month + "." + year;
                mDisplayDate.setText(dateTextView);
            }
        };
    }

}
