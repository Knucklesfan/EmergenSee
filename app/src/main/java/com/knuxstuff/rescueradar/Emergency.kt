package com.knuxstuff.rescueradar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(apiserv :APIService) {
    val context = LocalContext.current;
    val backgroundShade = MaterialTheme.colors.background //THIS IS SO DUMB!!!
    var backgroundColor by remember { mutableStateOf(backgroundShade) } // Default background
    val animatedColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ), // Adjust color change speed
        label = "Background Color"
    )
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Emergency Button")
                }
            )
        },
    ) { innerpadding ->
    //    val activity = LocalView.current.context as? Activity
    Box(modifier = Modifier
        .background(animatedColor)
        .fillMaxSize()
        .padding(innerpadding)
        .clickable(
            interactionSource = interactionSource,
            indication = null // Hides the button clicking indicator from clicking the background
        ) { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // Ensures the column spans the full width
                .fillMaxHeight() // and height
                .padding(horizontal = 24.dp), // Adds padding on both sides
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Bottom (Red) Clickable Area
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .padding(vertical = 100.dp)
                    /*.shadow(20.dp, RoundedCornerShape(50.dp)) // Cool shadow effect (It's just drop shadow)*/
                    .clip(RoundedCornerShape(50.dp)) // Round those corners
                    .background(Color.Red) // Placeholder colors
                    .clickable {
                        //                            val lm =
                        //                                getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                        //                            val location = if (ActivityCompat.checkSelfPermission(
                        //                                    this,
                        //                                    Manifest.permission.ACCESS_FINE_LOCATION
                        //                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        //                                    this,
                        //                                    Manifest.permission.ACCESS_COARSE_LOCATION
                        //                                ) != PackageManager.PERMISSION_GRANTED
                        //                            ) {
                        //                                // TODO: Consider calling
                        //                                //    ActivityCompat#requestPermissions
                        //                                // here to request the missing permissions, and then overriding
                        //                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                //                                          int[] grantResults)
                        //                                // to handle the case where the user grants the permission. See the documentation
                        //                                // for ActivityCompat#requestPermissions for more details.
                        //                                return
                        //                            } else {
                        //
                        //                            }
                        //                            lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        //                            val longitude = location!!.longitude
                        //                            val latitude = location!!.latitude
                        val longitude: Long = 5;
                        val latitude: Long = 5;
                        apiserv.alert(
                            "7b3d45eb-1292-4a43-9614-93afca0590aa",
                            latitude,
                            longitude,
                            "stuff"
                        )
                        val thread = Thread {
                            try {
                                val client = OkHttpClient()
                                val request: Request = Request.Builder()
                                    .url("http://192.168.0.52:3000/alert?token=7b3d45eb-1292-4a43-9614-93afca0590aa&lat=15&lon=16&type=\"stuff\"")
                                    .build()

                                val call: Call = client.newCall(request)
                                val response: Response = call.execute()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        thread.start()

                        val sharedPrefs =
                            context.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
                        val emergencyNumber = sharedPrefs.getString("emergency_number", null)

                        if (!emergencyNumber.isNullOrBlank()) {
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:$emergencyNumber")
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                "Please set an emergency number in Settings",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }, // Will eventually give the user more feedback than just a color change
                contentAlignment = Alignment.Center

            ) {
                Text(
                    text = stringResource(R.string.instant_emergency),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}
}