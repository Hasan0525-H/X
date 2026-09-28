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
        setContent { CalculatorApp() }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("") }
    var firstNumber by remember { mutableStateOf(0.0) }
    var operation by remember { mutableStateOf("") }

    val buttons = listOf(
        "7","8","9","÷",
        "4","5","6","×",
        "1","2","3","-",
        "C","0","=","+"
    )

    fun calculate() {
        val second = display.toDoubleOrNull() ?: 0.0
        val result = when(operation) {
            "+" -> firstNumber + second
            "-" -> firstNumber - second
            "×" -> firstNumber * second
            "÷" -> if (second != 0.0) firstNumber / second else 0.0
            else -> second
        }
        display = result.toString().removeSuffix(".0")
        operation = ""
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(display.ifEmpty { "0" }, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(20.dp))

        buttons.chunked(4).forEach { row ->
            Row(Modifier.fillMaxWidth()) {
                row.forEach { button ->
                    Button(
                        onClick = {
                            when {
                                button == "C" -> {
                                    display = ""
                                    operation = ""
                                }
                                button == "=" -> calculate()
                                button in listOf("+","-","×","÷") -> {
                                    firstNumber = display.toDoubleOrNull() ?: 0.0
                                    operation = button
                                    display = ""
                                }
                                else -> display += button
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    ) { Text(button) }
                }
            }
        }
    }
}
