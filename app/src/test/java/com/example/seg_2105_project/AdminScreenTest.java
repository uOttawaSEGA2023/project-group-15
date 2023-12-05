package com.example.seg_2105_project;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;


import com.example.seg_2105_project.Frontend.AdminActivities.AdminScreen;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class AdminScreenTest {

    @Rule
    public ActivityScenarioRule<AdminScreen> activityScenarioRule =
            new ActivityScenarioRule<>(AdminScreen.class);

    @Test
    public void testWelcomeMessageDisplayed() {
        // Launch the activity
        ActivityScenario<AdminScreen> scenario = activityScenarioRule.getScenario();

        // Check if the welcome message is displayed correctly
        String welcomeText = "Welcome, you are signed in as Administrator!";
        scenario.onActivity(activity -> {
            Espresso.onView(withId(R.id.welcomeMessage))
                    .check(matches(withText(welcomeText)));
        });
    }

    @Test
    public void testSignOutButton() {
        // Launch the activity
        ActivityScenario<AdminScreen> scenario = activityScenarioRule.getScenario();

        // Perform click on the sign-out button
        scenario.onActivity(activity -> {
            Espresso.onView(withId(R.id.signOutButton))
                    .perform(ViewActions.click());

            // Check if the activity transitions to WelcomeScreen
            Espresso.onView(ViewMatchers.withId(R.id.button))
                    .check(matches(ViewMatchers.isDisplayed()));
        });
    }

    @Test
    public void testInboxRegistrationsButton() {
        // Launch the activity
        ActivityScenario<AdminScreen> scenario = activityScenarioRule.getScenario();

        // Perform click on the Inbox Registrations button
        scenario.onActivity(activity -> {
            Espresso.onView(withId(R.id.inboxRegistrations))
                    .perform(ViewActions.click());

            // Check if the activity transitions to RegistrationsInbox
            Espresso.onView(ViewMatchers.withId(R.id.listViewRegistrationRequests))
                    .check(matches(ViewMatchers.isDisplayed()));
        });
    }

    @Test
    public void testRejectedRegistrationsButton() {
        // Launch the activity
        ActivityScenario<AdminScreen> scenario = activityScenarioRule.getScenario();

        // Perform click on the Rejected Registrations button
        scenario.onActivity(activity -> {
            Espresso.onView(withId(R.id.rejectedRegistrations))
                    .perform(ViewActions.click());

            // Check if the activity transitions to RejectedRegistrations
            Espresso.onView(ViewMatchers.withId(R.id.listViewRejectedRegistrations))
                    .check(matches(ViewMatchers.isDisplayed()));
        });
    }
}

