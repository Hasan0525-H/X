package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    var darkMode by remember { mutableStateOf(false) }
    val history = remember { mutableStateListOf<String>() }

    val buttons = listOf("7","8","9","÷","4","5","6","×","1","2","3","-",".","0","=","+")

    MaterialTheme(darkColorScheme = if (darkMode) darkColorScheme() else lightColorScheme()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { darkMode = !darkMode }) {
                    Text(if (darkMode) "☀" else "☾")
                }
            }

            Text(
                text = display.ifEmpty { "0" },
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            buttons.chunked(4).forEach { row ->
                Row(Modifier.fillMaxWidth()) {
                    row.forEach { button ->
                        Button(
                            onClick = {
                                when {
                                    button == "=" -> {
                                        val second = display.toDoubleOrNull() ?: 0.0
                                        val result = when(operation) {
                                            "+" -> firstNumber + second
                                            "-" -> firstNumber - second
                                            "×" -> firstNumber * second
                                            "÷" -> if (second != 0.0) firstNumber / second else 0.0
                                            else -> second
                                        }
                                        val answer = result.toString().removeSuffix(".0")
                                        history.add("$firstNumber $operation $second = $answer")
                                        display = answer
                                        operation = ""
                                    }
                                    button in listOf("+","-","×","÷") -> {
                                        firstNumber = display.toDoubleOrNull() ?: 0.0
                                        operation = button
                                        display = ""
                                    }
                                    else -> display += button
                                }
                            },
                            shape = CircleShape,
                            modifier = Modifier.weight(1f).padding(6.dp).height(64.dp)
                        ) { Text(button) }
                    }
                }
            }

            Button(
                onClick = { display = ""; firstNumber = 0.0; operation = ""; history.clear() },
                modifier = Modifier.fillMaxWidth().padding(6.dp)
            ) { Text("مسح") }

            history.takeLast(3).forEach { Text(it) }
        }
    }
}
