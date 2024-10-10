package com.shiftscheduler;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.persistence.DatabaseHelper;
import com.shiftscheduler.presentation.LoginActivity;
import com.shiftscheduler.presentation.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RegisterTest {
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
    public void registerTest() {

        //go to the register page
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.name)).perform(typeText("Charlie"));
        onView(withId(R.id.password)).perform(typeText("Charlie"));
        onView(withId(R.id.confirm_password)).perform(typeText("Charlie"));
        onView(withId(R.id.employee_code)).perform(typeText("3G5I7K"));
        closeSoftKeyboard();
        onView(withId(R.id.register_button)).perform(click());

        // login and go to the home page
        onView(withId(R.id.name)).perform(typeText("Charlie"));
        onView(withId(R.id.password)).perform(typeText("Charlie"));
        closeSoftKeyboard();
        onView(withId(R.id.login_button)).perform(click());

        // verify that it was login successfully
        onView(withId(R.id.tv_welcome)).check(matches(withText("Welcome, Charlie(employee)!")));

    }


}
