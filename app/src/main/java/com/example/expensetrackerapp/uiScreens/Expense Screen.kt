package com.example.expensetrackerapp.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetrackerapp.ui.theme.ExpenseRed
import com.example.expensetrackerapp.ui.theme.IncomeGreen
import com.example.expensetrackerapp.ui.theme.SurplusBlue
import com.example.expensetrackerapp.uiElements.ArrowUp
import com.example.expensetrackerapp.uiElements.ColouredIconButton
import com.example.expensetrackerapp.uiElements.TriggerExpenseDialogue
import com.example.expensetrackerapp.uiElements.TriggerIncomeDialogue
import com.example.expensetrackerapp.viewmodels.ExpensesViewModel

@Composable
fun ExpenseScreen(
    tab: String,
    expensesViewModel: ExpensesViewModel = viewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (tab) {
            "Summary" -> BuildSummaryScreen(expensesViewModel)
            else -> BuildExpensesScreens(tab, expensesViewModel)
        }
    }
}

@Composable
fun BuildSummaryScreen(viewModel: ExpensesViewModel) {
    var totalIncome by remember { mutableDoubleStateOf(0.0) }

    var totalExpenses by remember { mutableDoubleStateOf(0.0) }
    totalExpenses = viewModel.sumExpenses()

    var showIncomeDialogue by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Summary ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Incoming Row
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(IncomeGreen)
                .padding(10.dp)
                .height(40.dp)
                .width(300.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ArrowUp,
                contentDescription = "Income Up Arrow",
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Incoming: $totalIncome",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Spending Row
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(ExpenseRed)
                .padding(10.dp)
                .height(40.dp)
                .width(300.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ArrowUp,
                contentDescription = "Spending Down Arrow",
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp)
                    .rotate(180f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Spending: $totalExpenses",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Surplus/Deficit Row
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(SurplusBlue)
                .padding(10.dp)
                .height(40.dp)
                .width(300.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Surplus/Deficit: ${totalIncome - totalExpenses}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                showIncomeDialogue = true
            }
        ) {
            Text(
                text = "ADD INCOME",
                style = MaterialTheme.typography.titleSmall
            )
        }

        if (showIncomeDialogue) {
            TriggerIncomeDialogue(
                income = { income ->
                    totalIncome = income
                },
                onDismiss = { showIncomeDialogue = false }
            )
        }
    }
}

@Composable
fun BuildExpensesScreens(tab: String, viewModel: ExpensesViewModel) {
    val tabExpenses = viewModel.allExpenses.getOrPut(tab) { mutableStateMapOf() }
    var showDialogue by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$tab Expenses",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .weight(1f)
        ) {
            items(tabExpenses.entries.toList()) { currentExpense ->
                Row {
                    Text(
                        text = currentExpense.key,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.width(150.dp))
                    Text(
                        text = "$${currentExpense.value}/m",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ColouredIconButton(
                circleColour = MaterialTheme.colorScheme.primaryContainer,
                crossColour = Color.White,
                onClick = { showDialogue = true }
            )

            if (showDialogue) {
                TriggerExpenseDialogue(
                    onDismiss = { showDialogue = false },
                    expenseMap = tabExpenses,
                    onExpenseAdded = { viewModel.allExpenses[tab] = tabExpenses }
                )
            }
        }
    }
}
