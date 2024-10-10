package com.shiftscheduler;

import android.view.View;
import android.widget.TimePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

public class TimePickerActions {

    public static ViewAction setTime(final int hours, final int minutes) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }

            @Override
            public String getDescription() {
                return "Set the time on the TimePicker";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TimePicker timePicker = (TimePicker) view;
                timePicker.setHour(hours);
                timePicker.setMinute(minutes);
            }
        };
    }
}
