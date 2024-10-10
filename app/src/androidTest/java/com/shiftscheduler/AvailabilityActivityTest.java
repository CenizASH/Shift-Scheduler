package com.shiftscheduler;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.shiftscheduler.CalendarViewActions.setDate;
import static com.shiftscheduler.TimePickerActions.setTime;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;

import android.content.Context;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.persistence.DatabaseHelper;
import com.shiftscheduler.presentation.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class AvailabilityActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MyApplication myApp = (MyApplication) appContext.getApplicationContext();
        DatabaseHelper.setupDb(myApp.getApplicationContext());
    }

    @After
    public void tearDown() {
        DatabaseHelper.dropTables();
    }

    @Test
    public void availabilityTest() {

        // login and go to the home page
        onView(withId(R.id.name)).perform(typeText("manager"));
        onView(withId(R.id.password)).perform(typeText("manager"));
        onView(withId(R.id.login_button)).perform(click());
        closeSoftKeyboard();

        // click on the availability button
        onView(withId(R.id.btn_availability)).perform(click());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JULY, 22);
        long date = calendar.getTimeInMillis();
        onView(allOf(withId(R.id.calendarView),hasChildCount(1))).perform(setDate(date));
        onView(allOf(withId(R.id.calendarView),hasChildCount(3))).perform(click());

        onView(withId(R.id.add_availability)).perform(click());

        onView(withId(R.id.start_time_picker)).perform(setTime(8, 0));
        onView(withId(R.id.end_time_picker)).perform(setTime(10, 0));
        onView(withId(R.id.submit_button)).perform(click());

        // check if the availability is added: there should be at least one availability
        onData(anything()).inAdapterView(withId(R.id.availability_list)).atPosition(0).check(matches(isDisplayed()));
    }

}
