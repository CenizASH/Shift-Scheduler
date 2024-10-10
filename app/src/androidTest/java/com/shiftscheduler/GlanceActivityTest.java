package com.shiftscheduler;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.contains;

import android.content.Context;

import androidx.test.espresso.ViewAssertion;
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

public class GlanceActivityTest {
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
    public void glanceTest() {

        // login and go to the home page
        onView(withId(R.id.name)).perform(typeText("manager"));
        onView(withId(R.id.password)).perform(typeText("manager"));
        onView(withId(R.id.login_button)).perform(click());
        closeSoftKeyboard();

        // click on the glance button
        onView(withId(R.id.btn_glance)).perform(click());

        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        // verify title is correct
        onView(withId(R.id.title)).check(matches(withText("Week of "+ monday.toString())));
    }

}
