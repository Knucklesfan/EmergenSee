package com.knuxstuff.rescueradar

import android.graphics.ColorSpace
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EmergencyScreen(navigation: NavController) {
    var backgroundColor by remember { mutableStateOf(Color(0xFF3d4a70)) } // Default background
    val animatedColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing), // Adjust color change speed
        label = "Background Color")
    val interactionSource = remember { MutableInteractionSource() }



//    val activity = LocalView.current.context as? Activity
    Box(modifier = Modifier
        .background(animatedColor).fillMaxSize()
        .clickable (
            interactionSource = interactionSource,
            indication = null // Hides the button clicking indicator from clicking the background
        ) { backgroundColor = Color(0xFF3d4a70) }
    ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Ensures the column spans the full width
                    .fillMaxHeight() // and height
                    .padding(horizontal = 24.dp), // Adds padding on both sides
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top (Gray) Clickable Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 100.dp)
                        /*.shadow(20.dp, RoundedCornerShape(50.dp)) // Cool shadow effect (It's just drop shadow)*/
                        .clip(RoundedCornerShape(50.dp)) // Round those corners
                        .background(Color.LightGray) // Placeholder colors
                        .clickable { backgroundColor = Color.Gray }, //Will pull up some sort of screen for more detailed reporting
                contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.detailed_emergency),
                        style = TextStyle(color = Color.Black,
                            fontFamily = FontFamily.Serif,
                            fontSize = 35.sp))
                }

                // Bottom (Red) Clickable Area
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .padding(vertical = 100.dp)
                        /*.shadow(20.dp, RoundedCornerShape(50.dp)) // Cool shadow effect (It's just drop shadow)*/
                        .clip(RoundedCornerShape(150.dp)) // Round those corners
                        .background(Color(0xFFC21A1A)) // Placeholder colors
                        .clickable { backgroundColor = Color(0xFFff3b3b) }, // Will eventually give the user more feedback than just a color change
                    contentAlignment = Alignment.Center

                ) {
                    Text(text = stringResource(R.string.instant_emergency),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center)
                    )
                }
            }
    }
}