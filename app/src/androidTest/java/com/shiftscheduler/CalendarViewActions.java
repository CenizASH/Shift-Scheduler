package com.shiftscheduler;

import android.view.View;
import android.widget.CalendarView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class CalendarViewActions {

    public static ViewAction setDate(final long date) {
        return new SetCalendarDateAction(date);
    }


}
