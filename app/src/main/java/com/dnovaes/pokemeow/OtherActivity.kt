package com.dnovaes.pokemeow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class OtherActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        println("logd Im at OtherActivity")
        super.onCreate(savedInstanceState)
        setContent {
            OtherScreen()
        }
    }
}

@Composable
fun OtherScreen() {
    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)
    Button(onClick = { context.startActivity(intent) }) {
        Text("Go to MainActivity")
    }
}
