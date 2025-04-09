package com.knuxstuff.rescueradar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen() {

    // Mutable variables that default to false
    var isExpanded by remember { mutableStateOf(false) }  // Checks if dropdown is expanded
    var type by remember { mutableStateOf("") } // Holds the value of the dropdown
    var description by remember { mutableStateOf("") } // Holds the value of the description
    var emergencyError by remember { mutableStateOf(false) } // Validates dropdown is selected
    var descriptionError by remember { mutableStateOf(false) } // Validates description is filled
    var thankYou by remember { mutableStateOf(false) } // Shows thank you message


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Report Danger")
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally // All child elements are horizontally centered
        ) {
//        // Banner at the top of page
//        Box(
//            modifier = Modifier
//                .background(bannerColor)
//                .fillMaxWidth()
//                .height(70.dp),
//            contentAlignment = Alignment.Center // Centers text below
//        ) {
//            Text(
//                text = "Create Report",
//                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.Serif,
//                color = Color.White
//            )
//        }

            // Create space between the banner and the next element
            Spacer(modifier = Modifier.height(60.dp))

            // Dropdown menu
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                TextField(
                    value = type,
                    onValueChange = {},
                    readOnly = true,
                    isError = emergencyError,
                    placeholder = { Text("Type of Emergency") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(0.8f)
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    listOf(
                        "Accident", "Assault", "Crime", "Fire", "Medical", "Natural Disaster", "Other (Specify)"
                    ).forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                type = option
                                isExpanded = false
                                emergencyError = false // Clear error if fixed
                            }
                        )
                    }
                }
            }
            // Show error message below dropdown if not selected when submitted
            if (emergencyError) {
                Text(
                    text = "Please select an emergency type",
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp)
                        .fillMaxWidth(0.8f)
                )
            }

            // Create space between the dropdown and the next element
            Spacer(modifier = Modifier.height(50.dp))

            // Description TextField
            TextField(
                value = description,
                onValueChange = {
                    description = it
                    if (descriptionError && it.isNotBlank()) {
                        descriptionError = false // Clear error if fixed
                    }
                },
                label = { Text("Description of Emergency") },
                isError = descriptionError,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            // Show error message below text box if no description when submitted
            if (descriptionError) {
                Text(
                    text = "Please enter a description",
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp)
                        .fillMaxWidth(0.8f)
                )
            }
            // Create space between the description box and the next element
            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    // Check if the type and description are set
                    val isEmergencyValid = type.isNotBlank()
                    val isDescriptionValid = description.isNotBlank()

                    // Update error states
                    emergencyError = !isEmergencyValid
                    descriptionError = !isDescriptionValid

                    if (isEmergencyValid && isDescriptionValid) {

                        // Clear fields after submit
                        type = ""
                        description = ""

                        // Show thank you message
                        thankYou = true
                    } else {
                        // Hide thank you if validation fails
                        thankYou = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            ) {
                Text("Submit")
            }
            // Create space between the submit button and the next element
            Spacer(modifier = Modifier.height(16.dp))

            // Say thank you to verify submission
            if (thankYou) {
                Text(
                    text = "Thank you for your submission!",
                    color = Color.Green,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }    }// This is the vertical layout for the page. Everything on page falls under this column

}
