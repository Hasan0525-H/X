package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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

    val buttons = listOf("7","8","9","÷","4","5","6","×","1","2","3","-",".","0","=","+")

    fun calculate() {
        val second = display.toDoubleOrNull() ?: return
        display = when(operation) {
            "+" -> firstNumber + second
            "-" -> firstNumber - second
            "×" -> firstNumber * second
            "÷" -> if (second != 0.0) firstNumber / second else Double.NaN
            else -> second
        }.toString().removeSuffix(".0")
        operation = ""
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = display.ifEmpty { "0" },
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        buttons.chunked(4).forEach { row ->
            Row(Modifier.fillMaxWidth()) {
                row.forEach { button ->
                    Button(
                        onClick = {
                            when {
                                button == "=" -> calculate()
                                button in listOf("+","-","×","÷") -> {
                                    firstNumber = display.toDoubleOrNull() ?: 0.0
                                    operation = button
                                    display = ""
                                }
                                else -> display += button
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).padding(5.dp)
                    ) { Text(button) }
                }
            }
        }

        Button(
            onClick = { display = ""; operation = "" },
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) { Text("C") }
    }
}
