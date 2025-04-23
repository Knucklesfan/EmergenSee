package com.knuxstuff.rescueradar

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavBar(navigation: NavController, cont: @Composable () -> Unit): Unit {
    var flip by remember { mutableStateOf(false) }
    val rotateAnimate: Float by animateFloatAsState(if (flip) 360.0f else 0.0f, label = "alpha",     animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),)    // using the LaunchedEffect composable to create a scope
    // just for the duration of the specified key value.

    ModalNavigationDrawer(
        content = cont,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxHeight()) { //make sure that the box fills the entire thing so we can properly space stuff
                    //upper half
                    Column() {
                        Box(modifier = Modifier.fillMaxWidth()) {

                            Image(

                                ImageVector.vectorResource(R.drawable.justlogo),
                                contentDescription = stringResource(R.string.app_name),
                                modifier = Modifier.graphicsLayer(

                                    transformOrigin = TransformOrigin(
                                        0.5f,
                                        71f/126f
                                    ),
                                    rotationZ = rotateAnimate


                                ).align(Alignment.Center)
                                    .padding(8.dp).clip(CircleShape).clickable {
                                        flip = !flip;
                                        Log.d("Stuff", "Flip! $flip")
                                    }

                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                ImageVector.vectorResource(R.drawable.justname),
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
                            onClick = {navigation.navigate(Screen.Emergency)}
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
                            onClick = {navigation.navigate(Screen.Map)}
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
                            onClick = {navigation.navigate(Screen.Report)}
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
                            onClick = {navigation.navigate(Screen.History)}
                        )


                    }

                    //lower half
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
                            onClick = {navigation.navigate(Screen.Settings)}
                        )
                        HorizontalDivider()
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(R.string.copyright),
                            style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    )
}
//initializing the data class with default parameters
data class navItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String
) {

    //function to get the list of bottomNavigationItems
}
@Composable
fun bottomNavigationItems() : List<navItem> {
    return listOf(
        navItem(
            label = stringResource(R.string.emergency_drawer),
            icon = ImageVector.vectorResource(R.drawable.emergency),
            route = Screen.Emergency.route
        ),
        navItem(
            label = stringResource(R.string.heatmap_drawer),
            icon = ImageVector.vectorResource(R.drawable.map),
            route = Screen.Map.route
        ),
        navItem(
            label = stringResource(R.string.report_drawer),
            icon = ImageVector.vectorResource(R.drawable.flag),
            route = Screen.Report.route
        ),
        navItem(
            label = stringResource(R.string.history_drawer),
            icon = ImageVector.vectorResource(R.drawable.history),
            route = Screen.History.route
        ),
        navItem(
            label = stringResource(R.string.settings_drawer),
            icon = ImageVector.vectorResource(R.drawable.accounts),
            route = Screen.Settings.route
        ),

        )
}

@Composable
fun BottomNavBar(navigation: NavController, cont: @Composable () -> Unit) {
//initializing the default selected item
    val navBackStackEntry by navigation.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    /**
     * by using the rememberNavController()
     * we can get the instance of the navController
     */

//scaffold to hold our bottom navigation Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                //getting the list of bottom navigation items for our data class
                bottomNavigationItems().forEachIndexed {index,navigationItem ->

                    //iterating all items with their respective indexes
                    NavigationBarItem(
                        selected = currentRoute == navigationItem.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navigation.navigate(navigationItem.route) {
                                popUpTo(navigation.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            cont()
        }
    }
}