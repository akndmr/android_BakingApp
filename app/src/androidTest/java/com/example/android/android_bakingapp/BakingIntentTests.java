package com.example.android.android_bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.android_bakingapp.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class BakingIntentTests {
    /**
     * This test demonstrates Espresso Intents using the IntentsTestRule, a class that extends
     * ActivityTestRule. IntentsTestRule initializes Espresso-Intents before each test that is annotated
     * with @Test and releases it once the test is complete. The designated Activity
     * is also terminated after each test.
     */

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickRecipe_SendIntentToPackageAndHasIntentWithKey() {
        //gives time to get the data
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipes_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // intended(Matcher<Intent> matcher) asserts the given matcher matches one and only one
        // intent sent by the application.
        intended(allOf(hasExtraWithKey(Utils.BUNDLE_STEPS), toPackage("com.example.android.android_bakingapp")));
    }
}