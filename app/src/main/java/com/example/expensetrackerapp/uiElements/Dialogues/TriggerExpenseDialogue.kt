package com.example.expensetrackerapp.uiElements.Dialogues

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TriggerExpenseDialogue(
    onDismiss: () -> Unit,
    expenseMap: MutableMap<String, Double>,
    onExpenseAdded: () -> Unit
) {
    var expenseName by remember { mutableStateOf("") }
    var expenseValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.height(250.dp)
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add Expense",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Name: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    TextField(
                        value = expenseName,
                        onValueChange = { text ->
                            expenseName = text
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Cost: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    TextField(
                        value = expenseValue,
                        onValueChange = { text ->
                            expenseValue = text
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                val doubleExpenseValue: Double? = expenseValue.toDoubleOrNull()

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (doubleExpenseValue != null) {
                            if (expenseName.isNotBlank() && doubleExpenseValue > 0 && !expenseMap.containsKey(expenseName)) {
                                expenseMap[expenseName] = doubleExpenseValue
                                onExpenseAdded()
                            } else {
                                Toast.makeText(context, "Invalid expense", Toast.LENGTH_SHORT).show()
                            }
                        }
                        onDismiss()
                    }
                ) {
                    Text("Save", style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}