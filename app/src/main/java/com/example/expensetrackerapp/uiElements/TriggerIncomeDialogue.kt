package com.example.expensetrackerapp.uiElements

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerIncomeDialogue(
    income: (Double) -> Unit,
    onDismiss: () -> Unit
) {

    var incomeValue by remember { mutableStateOf("") }
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
                    text = "Add Income",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = incomeValue,
                    onValueChange = { text ->
                        incomeValue = text
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(10.dp))

                val incomeDouble = incomeValue.toDoubleOrNull()
                Button(
                    onClick = {
                        if (incomeDouble != null) {
                            if (incomeValue.isNotBlank() && incomeDouble > 0) {
                                income(incomeDouble)
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