package com.example.android.android_bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding OrderActivity.
 *
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class BakingAppTests {

    //public static final String STEP_TITLE= "Starting prep";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_OpensActivityWithIngredients() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients_tv)).check(matches(isDisplayed()));



    }

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_OpensActivityWithRecipeSteps() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.steps_rv)).check(matches(isDisplayed()));


    }
/*
    @Test
    public void activityResult_DisplaysContactsPhoneNumber() {
        // Build the result to return when the activity is launched.
        Intent resultData = new Intent();
        String recipe_name = "Test Recipe";
        resultData.putExtra("recipe_name", recipe_name);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "contacts" is seen.
        intending(toPackage("com.example.android.android_bakingapp")).respondWith(result);

        // User action that results in "contacts" activity being launched.
        // Launching activity expects phoneNumber to be returned and displayed.
        onView(withId(R.id.steps_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Assert that the data we set up above is shown.
        onView(withId(R.id.recipe_tv)).check(matches(withText(recipe_name)));
    }

*/
}