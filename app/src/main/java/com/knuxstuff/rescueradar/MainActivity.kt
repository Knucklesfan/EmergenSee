package com.knuxstuff.rescueradar

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.knuxstuff.rescueradar.ui.theme.RescueRadarTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavController
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_POWER)) {

            //Do something
            Log.d("Debug","BUTTON PRESS DETECTED " + (navController.currentDestination.toString() == Screen.Emergency::class.qualifiedName).toString())
            return true;
        }
        return super.onKeyDown(keyCode, event)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RescueRadarTheme {
                navController = rememberNavController()
                NavStack(navController as NavHostController)
            }

//                    when(currentDestination) {
//                        0->{
//                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                Greeting(
//                                    name = "Jalen",
//                                    modifier = Modifier.padding(innerPadding)
//                                )
//                            }
//                        }
//                        1->{
//                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                Greeting(
//                                    name = "Montre",
//                                    modifier = Modifier.padding(innerPadding)
//                                )
//                            }
//                        }
//                        2->{
//                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                Greeting(
//                                    name = "Karmen",
//                                    modifier = Modifier.padding(innerPadding)
//                                )
//                            }
//
//                        }
//                        3->{
//                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                Greeting(
//                                    name = "Isaiah",
//                                    modifier = Modifier.padding(innerPadding)
//                                )
//                            }
//
//                        }
//                        4->{
//                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                Greeting(
//                                    name = "Jared",
//                                    modifier = Modifier.padding(innerPadding)
//                                )
//                            }
//
//                        }
//                    }
//                }
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