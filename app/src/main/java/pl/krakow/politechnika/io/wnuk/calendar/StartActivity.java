package pl.krakow.politechnika.io.wnuk.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    private TextView tv_welcome;
    private ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tv_welcome = (TextView) findViewById(R.id.tv_welcome);
        iv_icon = (ImageView) findViewById(R.id.iv_icon_earth);


        /**
         * Start activity opening and timing
         */
        final Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
