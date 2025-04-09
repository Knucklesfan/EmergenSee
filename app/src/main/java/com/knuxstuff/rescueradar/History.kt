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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


// Class that defines the properties of each report
data class Report(val title: String, val date: String, val location: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {

    // Create a coroutineScope for refreshing the data
    val coroutineScope = rememberCoroutineScope()
    val backColor = Color(0xFF3d4a70)
    val bannerColor = Color(0xFFC21A1A)
    // Holds the reports
    var reports by remember { mutableStateOf(sampleReports) }

    // Box layout to hold UI
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("History & Logging")
                }
            )
        },
    ) { innerPadding ->
        Column (
                horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer).padding(16.dp)
                .fillMaxSize()
                .padding(innerPadding),

        ) {
            // Main Column

                // Top of public report tab

                    // Displays List of Reports
                    LazyColumn(
                        modifier = Modifier

                            .fillMaxWidth()
                    ) {

                        // Creates a ReportCard for each report
                        items(reports) { report ->
                            ReportCard(report)
                        }
                    }
                }

                // Top of Your report tab (When scrolling is implemented)
                /*Box(
                modifier = Modifier
                    .background(Color(0xFF7e8ebd))
                    .padding(16.dp)
                    .wrapContentSize()

            ) {
                Text(
                    text = "Your Reports",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
            // Displays List of Reports
            LazyColumn (
                modifier = Modifier
                    .background(Color(0xFF7e8ebd))
                    .fillMaxWidth()
                    .padding(16.dp),
            ){

                // Creates a ReportCard for each report
                items(reports) { report ->
                    ReportCard(report)
                }
            }*/
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
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),
    Report("House Fire", "2025-03-01", "Denton, TX"),
    Report("Car Accident", "2025-03-02", "Jefferson, TX"),

    Report("Robbery", "2025-03-03", "Tyler, TX")

)


// OTHER IDEAS FOR DESIGN:
// ICONS FOR EACH EVENT (SOME CUSTOM GRAPHICS)
// SORT AND FILTER (MAY NEED TO BE DONE IN BACKEND)
// LAYOUT ANIMS?