package com.knuxstuff.rescueradar

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.knuxstuff.rescueradar.ui.theme.RescueRadarTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController
    private var showLoc = false;
    private var showNotif = false;
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_POWER)) {

            //Do something
            Log.d("Debug","BUTTON PRESS DETECTED " + (navController.currentDestination.toString() == Screen.Emergency::class.qualifiedName).toString())
            return true;
        }
        return super.onKeyDown(keyCode, event)

    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
            Log.w("notify","app can send notifications?!?!?")
            showNotif = true;

        } else {
            // TODO: Inform user that that your app will not show notifications.
            showNotif = true;
            Log.w("notify","app CANT SEND????")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        print("HELLO WROLD!!!")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                val w = Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var showNotifPopup by remember { mutableStateOf(showNotif)};
            var showLocPopup by remember { mutableStateOf(showNotif)};
            // Declare the launcher at the top of your Activity/Fragment:

            RescueRadarTheme {
                navController = rememberNavController()
                NavStack(navController as NavHostController)
                if(showNotifPopup) {
                    AlertDialogExample({},{},"HELLO WORLD", "you seem not to have notifs enabled :/")
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
