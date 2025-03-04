package com.knuxstuff.rescueradar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable


@Serializable
sealed class Screen(val route: String) { //list of all possible screens, and their routing
    @Serializable
    data object Emergency: Screen("emergency")

    @Serializable
    data object Map: Screen("heatmap")

    @Serializable
    data object Report: Screen("report")

    @Serializable
    data object History: Screen("history")

    @Serializable
    data object Settings: Screen("settings")

} //I LOVE KOTLIN SOOOO MUCH /s
//Why isn't this an enum? What is the obsession with adding unnecessary complexity? I DONT KNOW.

@Composable
fun NavStack(navController: NavHostController) {

    NavBar(navigation = navController) {
        NavHost(navController = navController, startDestination = Screen.Emergency) {
            composable<Screen.Emergency> { EmergencyScreen( /* ... */ ) }
            composable<Screen.Map> { MapScreen( /* ... */ ) }
            composable<Screen.Report> { ReportScreen( /* ... */ ) }
            composable<Screen.History> { HistoryScreen( /* ... */ ) }
            composable<Screen.Settings> { SettingsScreen(/* ... */ ) }
            // Add more destinations eventually...
        }
    }
}