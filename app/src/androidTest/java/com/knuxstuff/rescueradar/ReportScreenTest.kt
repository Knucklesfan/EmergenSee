package com.knuxstuff.rescueradar

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportScreenButtonTest {

    //Gets access to the dropdown
    @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test //Test that report is accepted if required values are filled in
    fun validFormTest() {
        composeTestRule.setContent {
            ReportScreen() //Get the ReportScreen function that is in 'Report.kt'
        }

        //Clicks on dropdown
        composeTestRule.onNodeWithText("Type of Emergency").performClick()
        //1 sec delay
        Thread.sleep(1000)

        //Selects 'fire' from dropdown
        composeTestRule.onNodeWithText("Fire").performClick()
        //1 sec delay
        Thread.sleep(1000)

        //Enters a description
        composeTestRule
            .onNodeWithText("Description of Emergency")
            .performTextInput("There's a fire near UT Tyler Varsity Dr.")
        //1 sec delay
        Thread.sleep(1000)

        //Clicks submit
        composeTestRule.onNodeWithText("Submit").performClick()

        //1 sec delay
        Thread.sleep(1000)

        //Verifies it is submitted
        composeTestRule
            .onNodeWithText("Thank you for your submission!")
            .assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test //Test that report is not accepted if required values are not filled in
    fun emptyFormTest() {
        composeTestRule.setContent {
            ReportScreen() //Get the ReportScreen function that is in 'Report.kt'
        }
        //1 sec delay
        Thread.sleep(1000)

        //Submits button without filling in required fields
        composeTestRule.onNodeWithText("Submit").performClick()
        //1 sec delay
        Thread.sleep(1000)

        //Verifies error for empty emergency type
        composeTestRule
            .onNodeWithText("Please select an emergency type")
            .assertIsDisplayed()
        //1 sec delay
        Thread.sleep(1000)

        //Verifies error for empty description
        composeTestRule
            .onNodeWithText("Please enter a description")
            .assertIsDisplayed()
        Thread.sleep(1000)
    }
}
