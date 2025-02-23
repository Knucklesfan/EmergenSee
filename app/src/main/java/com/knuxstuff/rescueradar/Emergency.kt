package com.knuxstuff.rescueradar

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun EmergencyScreen() {

//    val activity = LocalView.current.context as? Activity
    Box(modifier = Modifier.background(Color.Red).padding(16.dp).fillMaxSize()) {
        Text("This is the Emergency Screen", modifier = Modifier.align(Alignment.Center))
    }
}