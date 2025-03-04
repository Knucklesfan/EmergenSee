package com.knuxstuff.rescueradar

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val color = remember { Animatable(Color.Red) }
    // using the LaunchedEffect composable to create a scope
    // just for the duration of the specified key value.
    LaunchedEffect(Unit) {
        color.animateTo(Color.Red, animationSpec = tween(1500))
        color.animateTo(Color.Gray, animationSpec = tween(1500))
        color.animateTo(Color.Blue, animationSpec = tween(1500))
        color.animateTo(Color.Black, animationSpec = tween(1500))
        color.animateTo(Color.Yellow, animationSpec = tween(1500))
        color.animateTo(Color.Cyan, animationSpec = tween(1500))
    }
    Box(modifier = Modifier.background(Color.Magenta).padding(16.dp).fillMaxSize()) {
            Text("PRESS TO ALERT")

    }

}