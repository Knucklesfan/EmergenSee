package com.knuxstuff.rescueradar

import android.app.Activity
import android.graphics.drawable.Icon
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun EmergencyScreen() {

//    val activity = LocalView.current.context as? Activity
    Box(modifier = Modifier.background(Color.Red).fillMaxSize()) {
        Button(
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.fillMaxSize(),
            onClick = {print("hello world")},
            shape = RectangleShape

        ) {
            Column {
                Icon(
                    ImageVector.vectorResource(R.drawable.emergency),
                    modifier = Modifier.size(256.dp).align(Alignment.CenterHorizontally),
                    contentDescription = stringResource(R.string.emergency_drawer_icon),
                    tint = Color.White

                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),

                    text="ALERT",
                    fontSize = 96.sp,
                    color = Color.White
                )
            }
        }
    }
}