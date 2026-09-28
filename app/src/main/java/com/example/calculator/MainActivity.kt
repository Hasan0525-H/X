package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var value by remember { mutableStateOf("") }

    val buttons = listOf(
        "7","8","9","÷",
        "4","5","6","×",
        "1","2","3","-",
        "C","0","=","+"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = value, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))

        buttons.chunked(4).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { text ->
                    Button(
                        onClick = {
                            when(text) {
                                "C" -> value = ""
                                else -> value += text
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) {
                        Text(text)
                    }
                }
            }
        }
    }
}
