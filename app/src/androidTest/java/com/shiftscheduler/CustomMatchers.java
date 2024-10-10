package com.shiftscheduler;

import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {

    public static Matcher<View> withTableCell(final String text, final int row, final int column) {
        return new BoundedMatcher<View, TableLayout>(TableLayout.class) {
            @Override
            protected boolean matchesSafely(TableLayout table) {
                TableRow tableRow = (TableRow) table.getChildAt(row);
                if (tableRow != null && tableRow instanceof TableRow) {
                    View view = tableRow.getChildAt(column);
                    if (view != null && view instanceof TextView) {
                        return ((TextView) view).getText().toString().equals(text);
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text: " + text + " at row: " + row + " column: " + column);
            }
        };
    }
}