package com.knuxstuff.rescueradar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReportScreen() {
    Box(modifier = Modifier.background(Color.Yellow).padding(16.dp).fillMaxSize()) {
        Text("This is the Report Screen")
    }

}