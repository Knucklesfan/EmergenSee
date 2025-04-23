package com.knuxstuff.rescueradar

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.knuxstuff.rescueradar.ui.theme.RescueRadarTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:30000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIService = retrofit.create(APIService::class.java)

//        val potentialToken = this.getSharedPreferences("token",0);
//        if(potentialToken.getString("token","") == "") {
//            val deviceID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
//            val response = service.register(deviceID,"").enqueue(object:
//                Callback<RegistrationResult?> {
//                override fun onResponse(
//                    call: Call<RegistrationResult?>,
//                    response: Response<RegistrationResult?>
//                ) {
//                    if(response.isSuccessful) {
//                        if(response.body()?.success == true) {
//                            potentialToken.edit {
//                                putString("token", response.body()?.token ?: "")
//                                apply()
//                            }
//                        }
//                        else {
//                            Log.w("NETWORKING",response.body().toString())
//                        }
//                    }
//                    else {
//                        Log.w("NETWORKING",response.errorBody().toString())
//                    }
//                }
//
//                override fun onFailure(p0: Call<RegistrationResult?>, p1: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            });
//        }

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
        FirebaseMessaging.getInstance().subscribeToTopic("fcm_default_channel")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showNotifPopup by remember { mutableStateOf(showNotif)};
            var showLocPopup by remember { mutableStateOf(showNotif)};
            // Declare the launcher at the top of your Activity/Fragment:

            RescueRadarTheme {
                navController = rememberNavController()
                NavStack(navController as NavHostController, service)
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
