package com.shiftscheduler;

import static org.hamcrest.Matchers.isA;

import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import java.util.Calendar;

public class SetCalendarDateAction implements ViewAction {
    private final long date;

    public SetCalendarDateAction(long date) {
        this.date = date;
    }

    @Override
    public Matcher<View> getConstraints() {
//        return isA(View.class);
        return ViewMatchers.isAssignableFrom(CalendarView.class);
    }

    @Override
    public String getDescription() {
        return "Set the date on CalendarView";
    }

    @Override
    public void perform(UiController uiController, View view) {
        CalendarView calendarView = (CalendarView) view;
        calendarView.setDate(date,true, true);
    }
}
