package com.dnovaes.pokemeow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.dnovaes.pokemeow.inventory.InventoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: InventoryViewModel by viewModels()
        println("logd calling service to call getInventory")
        viewModel.getInventory()

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
