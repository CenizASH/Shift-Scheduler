package com.shiftscheduler;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

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

public class EmployeeActivityTest {
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
    public void employeeTest() {

        // login and go to the home page
        onView(withId(R.id.name)).perform(typeText("manager"));
        onView(withId(R.id.password)).perform(typeText("manager"));
        onView(withId(R.id.login_button)).perform(click());
        closeSoftKeyboard();

        // click on the manage button
        onView(withId(R.id.btn_manage)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.employee_list)).atPosition(0).perform(click());

        onView(withId(R.id.name)).check(matches(withText("Alice")));
    }

}
