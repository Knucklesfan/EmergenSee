package com.knuxstuff.rescueradar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily

// Class that defines the properties of each report
data class Report(val title: String, val date: String, val location: String)

@Composable
fun HistoryScreen() {

    // Create a coroutineScope for refreshing the data
    val coroutineScope = rememberCoroutineScope()
    // Holds the reports
    var reports by remember { mutableStateOf(sampleReports) }

    // Box layout to hold UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(16.dp)
    ) {
        Column {
            Text(text = "History & Logging", style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            ),
                modifier = Modifier
                    .fillMaxWidth()

                    // Padding Below the Text
                    .padding(bottom = 16.dp)
                    // Aligns Center
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            // Adds Vertical Space
            Spacer(modifier = Modifier.height(10.dp))

            // Displays List of Reports
            LazyColumn {
                // Creates a ReportCard for each report
                items(reports) { report ->
                    ReportCard(report)
                }
            }
        }

        // Refreshing report list
        Box(
            modifier = Modifier.fillMaxSize(),
            // Aligns the refresh button to the bottom right hand corner
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                    // Simulate refreshing data
                    coroutineScope.launch {
                        reports = reports.shuffled()
                    }
                },
                // Padding
                modifier = Modifier.padding(16.dp)
            ) {
                // Refresh Icon
                Icon(Icons.Filled.Refresh, "Refresh history")
            }
        }
    }
}

@Composable
fun ReportCard(report: Report) {
    // Card displays each report's details
    Card(
        modifier = Modifier
            // Card fills width
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        // Card background
        colors = CardDefaults.cardColors(containerColor = Color.White),
        // Card shadow
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        // Holds the content of the card
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = report.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Date: ${report.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Location: ${report.location}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Sample reports to test history and logging display
val sampleReports = listOf(
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("Robbery", "2025-03-03", "Tyler, TX")
)


// OTHER IDEAS FOR DESIGN:
// ICONS FOR EACH EVENT (SOME CUSTOM GRAPHICS)
// SORT AND FILTER (MAY NEED TO BE DONE IN BACKEND)
// LAYOUT ANIMS?