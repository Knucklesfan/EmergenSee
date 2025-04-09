package com.knuxstuff.rescueradar

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    // The red for the banner color in ARGB format
    val bannerColor = Color(0xFFC21A1A)
    // The blue for the background in ARGB format
    val backColor = Color(0xFF3d4a70)
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
    Box(modifier = Modifier
        .background(Color(0xFF3d4a70))
        .padding(16.dp)
        .fillMaxSize()) {


    }
    // Banner at the top of page
    Box(
        modifier = Modifier
            .background(bannerColor)
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.Center // Centers text below
    ) {
        Text(
            text = "Settings",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color.White
        )
    }

}