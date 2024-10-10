package com.shiftscheduler;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class StatisticsActivityTest {
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
    public void statisticsTest() {

        // login and go to the home page
        onView(withId(R.id.name)).perform(typeText("manager"));
        onView(withId(R.id.password)).perform(typeText("manager"));
        onView(withId(R.id.login_button)).perform(click());
        closeSoftKeyboard();

        // click on the working hour statistic button
        onView(withId(R.id.btn_work_hour)).perform(click());

        // verify the value in the second row, first column
        onView(withId(R.id.statistics_table))
                .check(matches(CustomMatchers.withTableCell("1A3B5C", 1, 0)));
    }

}
