package com.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by DivyaSethi on 02/10/18.
 */
public class EspressoUtils {

    public static void waitForViewUsingIdlingResource(int resourceId) {
        try {
            waitForViewToBeDisplayed(resourceId, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void waitForViewToBeDisplayed(int resourceId, int noOfattempts) throws Exception {
        final boolean viewDisplayed[] = {false};
        final int attempts[] = {0};
        while (!viewDisplayed[0]) {
            viewDisplayed[0] = true;
            onView(withId(resourceId)).withFailureHandler(new FailureHandler() {
                @Override
                public void handle(Throwable error, Matcher<View> viewMatcher) {
                    viewDisplayed[0] = false;
                    attempts[0]++;
                }
            }).check(matches(isDisplayed()));
            idleForView(2000);
            if (attempts[0] >= noOfattempts) {
                break;
            }
        }


    }

    public static void idleForView(long millis) throws Exception {
        // TODO: 28/12/17
        Thread.sleep(millis);
    }

    public static void clickOnRecyclerView(int id, int position) {
        onView(withId(id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
    }

    public static ViewAction customClick(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null; // no constraints, they are checked above
            }

            @Override
            public String getDescription() {
                return "click button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static void checkIsDisplayed(int id) {
        Espresso.onView(withId(id)).check(ViewAssertions.matches(isDisplayed()));
    }

    public static void checkIsDisplayedWithText(String text) {
        Espresso.onView(withText(text)).check(ViewAssertions.matches(isDisplayed()));
    }

    public static void checkIsDisplayedWithText(int resourceId, int stringId) {
        onView(allOf(withId(resourceId), withText(stringId))).check(matches(isDisplayed()));
    }

    public static void clickWithId(int id) {
        onView(withId(id)).perform(click());
    }

}