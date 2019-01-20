package pl.krakow.politechnika.io.wnuk.calendar;

import android.content.Intent;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import pl.krakow.politechnika.io.wnuk.calendar.color.ColorItem;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {


    @Test
    public void testColorItemFlagNumber(){
        ColorItem colorItem = new ColorItem("WHITE", 101020);
        assertEquals(101020, colorItem.getmFlagItem());
    }

    @Test
    public void testColorItemFlagNormal(){
        ColorItem colorItem = new ColorItem("WHITE", R.drawable.yellow);
        assertEquals(R.drawable.yellow, colorItem.getmFlagItem());
    }

    @Test
    public void testColorItemStringNormal(){
        ColorItem colorItem = new ColorItem("WHITE", 101020);
        assertEquals("WHITE", colorItem.getColorName());
    }

    @Test
    public void testColorItemStringWeird(){
        ColorItem colorItem = new ColorItem("WHITE !@#1 ASDASDSA", 101020);
        assertEquals("WHITE !@#1 ASDASDSA", colorItem.getColorName());
    }

    @Test
    public void testColorItemStringEmpty(){
        ColorItem colorItem = new ColorItem("", 101020);
        assertEquals("", colorItem.getColorName());
    }

}
