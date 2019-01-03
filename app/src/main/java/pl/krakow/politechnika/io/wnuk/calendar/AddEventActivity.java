package pl.krakow.politechnika.io.wnuk.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

import pl.krakow.politechnika.io.wnuk.calendar.color.ColorAdapter;
import pl.krakow.politechnika.io.wnuk.calendar.color.ColorItem;

public class AddEventActivity extends AppCompatActivity {

    private static final String TAG = "AddEventActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String title;
    private String date;
    private String dateText;
    private EditText titleInput;
    private Button submitButton;
    private Spinner colorSpinner;
    private String color;
    private ArrayList<ColorItem> colorList;
    private ColorAdapter colorAdapter;

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
        colorSpinner = (Spinner) findViewById(R.id.sColor);

        initList();
        colorAdapter = new ColorAdapter(this, colorList);
        colorSpinner.setAdapter(colorAdapter);

        /**
         * Color picker setup
         */
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color = ((ColorItem)adapterView.getItemAtPosition(i)).getColorName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                color = "WHITE";
            }
        });

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
                if(title.isEmpty() || date == null ){
                    if(title.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_title), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.error_date), Toast.LENGTH_LONG).show();
                    }
                }else {
                    intent.putExtra("title", title);
                    intent.putExtra("date", date);
                    intent.putExtra("color", color);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
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
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                date = year + "/" + month + "/" + day;
                dateText= day + "." + month + "." + year;
                mDisplayDate.setText(dateText);
            }
        };
    }

    /**
     * String to Color in Integeer
     * initialization list of colors
     */
    private void initList(){
        colorList = new ArrayList<>();
        colorList.add(new ColorItem("WHITE", R.drawable.white));
        colorList.add(new ColorItem("RED", R.drawable.red));
        colorList.add(new ColorItem("BLACK", R.drawable.black));
        colorList.add(new ColorItem("BLUE", R.drawable.blue));
        colorList.add(new ColorItem("YELLOW", R.drawable.yellow));
        colorList.add(new ColorItem("GRAY", R.drawable.gray));
    }
}
