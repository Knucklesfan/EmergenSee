package com.knuxstuff.rescueradar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knuxstuff.rescueradar.ui.theme.RescueRadarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RescueRadarTheme {
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            Box(modifier = Modifier.fillMaxHeight()) {
                                Column() {
                                    Box(modifier = Modifier.fillMaxWidth()) {

                                        Image(
                                            ImageVector.vectorResource(R.drawable.logo_name),
                                            contentDescription = stringResource(R.string.app_name),
                                            modifier = Modifier.align(Alignment.Center)
                                                .padding(8.dp)
                                        )
                                    }

                                    HorizontalDivider()
                                    NavigationDrawerItem(
                                        label = {
                                            Text(
                                                stringResource(R.string.emergency_drawer),
                                                color = Color.Red
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.emergency),

                                                contentDescription = stringResource(R.string.emergency_drawer_icon)
                                            )
                                        },
                                        selected = false,
                                        onClick = { println("hello world") }
                                    )
                                    NavigationDrawerItem(
                                        label = { Text(stringResource(R.string.heatmap_drawer)) },
                                        icon = {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.map),
                                                contentDescription = stringResource(R.string.heatmap_drawer_icon)
                                            )
                                        },
                                        selected = false,
                                        onClick = { println("hello world") }
                                    )
                                    NavigationDrawerItem(
                                        label = { Text(stringResource(R.string.report_drawer)) },
                                        icon = {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.flag),
                                                contentDescription = stringResource(R.string.report_drawer_icon)
                                            )
                                        },
                                        selected = false,
                                        onClick = { println("hello world") }
                                    )
                                    NavigationDrawerItem(
                                        label = { Text(stringResource(R.string.history_drawer)) },
                                        icon = {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.history),
                                                contentDescription = stringResource(R.string.history_drawer_icon)
                                            )
                                        },
                                        selected = false,
                                        onClick = { println("hello world") }
                                    )


                                }
                                Column (modifier = Modifier.align(Alignment.BottomStart)){
                                    HorizontalDivider()
                                    NavigationDrawerItem(
                                        label = {
                                            Text(
                                                stringResource(R.string.settings_drawer),
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.accounts),

                                                contentDescription = stringResource(R.string.emergency_drawer_icon)
                                            )
                                        },
                                        selected = false,
                                        onClick = { println("hello world") }
                                    )
                                    HorizontalDivider()
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = stringResource(R.string.copyright),
                                        style = MaterialTheme.typography.labelSmall)
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = stringResource(R.string.gnucopypasta),
                                        style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Nick",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RescueRadarTheme {
        Greeting("Android")
    }
}