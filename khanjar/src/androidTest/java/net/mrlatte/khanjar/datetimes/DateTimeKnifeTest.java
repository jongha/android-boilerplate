package net.mrlatte.khanjar.datetimes;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import java.util.Date;

/**
 * Created by Jongha on 2/14/16.
 */
public class DateTimeKnifeTest extends InstrumentationTestCase {

    @Test
    public void testGetString() throws Exception {
        String dateString = DateTimeKnife.getString(getInstrumentation().getContext(), new Date());
        assertNotNull(dateString);
    }

    @Test
    public void testGetMonthDayString() throws Exception {
        String dateString = DateTimeKnife.getMonthDayString(new Date());
        assertNotNull(dateString);
    }

    @Test
    public void testGetDateWithTime() throws Exception {
        String dateString = DateTimeKnife.getDateWithTime(new Date());
        assertNotNull(dateString);
    }

    @Test
    public void testGetHoursWithElapsedTime() throws Exception {
        String dateString = DateTimeKnife.getHoursWithElapsedTime(getInstrumentation().getContext(), new Date());
        assertNotNull(dateString);
    }

    @Test
    public void testGetHoursString() throws Exception {
        String dateString = DateTimeKnife.getHoursString(new Date());
        assertNotNull(dateString);
    }

    @Test
    public void testIsSameDay() throws Exception {
        boolean expectTrue = DateTimeKnife.isSameDay(new Date(), new Date());
        boolean expectFalse = DateTimeKnife.isSameDay(new Date(), new Date(new Date().getTime() + 1000 * 60 * 60 * 24));

        assertTrue(expectTrue);
        assertFalse(expectFalse);
    }

    @Test
    public void testGetDuration() throws Exception {
        assertEquals(DateTimeKnife.getDuration(0), "00:00");
        assertEquals(DateTimeKnife.getDuration(60), "01:00");
        assertEquals(DateTimeKnife.getDuration(3600), "1:00:00");

    }

    @Test
    public void testFromString() throws Exception {
        
    }
}