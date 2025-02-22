package com.example.expensetrackerapp.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetrackerapp.ui.theme.ExpenseRed
import com.example.expensetrackerapp.ui.theme.IncomeGreen
import com.example.expensetrackerapp.ui.theme.SurplusBlue
import com.example.expensetrackerapp.uiElements.Icons.ColouredIconButton
import com.example.expensetrackerapp.uiElements.Dialogues.TriggerExpenseDialogue
import com.example.expensetrackerapp.uiElements.Dialogues.TriggerIncomeDialogue
import com.example.expensetrackerapp.uiElements.Icons.ArrowUp
import com.example.expensetrackerapp.uiElements.Icons.Car
import com.example.expensetrackerapp.uiElements.Icons.Food
import com.example.expensetrackerapp.uiElements.PercentageBar
import com.example.expensetrackerapp.viewmodels.ExpensesViewModel
import com.example.expensetrackerapp.viewmodels.IncomeViewModel

@Composable
fun ExpenseScreen(
    tab: String,
    expensesViewModel: ExpensesViewModel = viewModel(),
    incomeViewModel: IncomeViewModel = viewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (tab) {
            "Summary" -> BuildSummaryScreen(expensesViewModel, incomeViewModel)
            else -> BuildExpensesScreens(tab, expensesViewModel)
        }
    }
}

@Composable
fun BuildSummaryScreen(
    expensesViewModel: ExpensesViewModel,
    incomeViewModel: IncomeViewModel
) {
    var totalExpenses by remember { mutableDoubleStateOf(0.0) }
    totalExpenses = expensesViewModel.sumExpenses()

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
                text = "Incoming: ${incomeViewModel.income.doubleValue}",
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
            val difference = incomeViewModel.income.doubleValue - totalExpenses
            val differenceType = if (difference >= 0) "Surplus" else "Deficit"
            Text(
                text = "$differenceType: $difference",
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
                    incomeViewModel.updateIncome(income)
                },
                onDismiss = { showIncomeDialogue = false }
            )
        }
        Spacer(modifier = Modifier.height(7.dp))

        // Percent Expense Summary
        Text(
            text = "Cost Breakdown:",
            style = MaterialTheme.typography.labelMedium
        )
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            val expenseTypes = listOf("Housing", "Food", "Transportation", "Other")
            val iconsByExpenseType = mapOf(
                "Housing" to Icons.Default.Home,
                "Food" to Food,
                "Transportation" to Car,
                "Other" to Icons.Default.Info
            )
            items(expenseTypes) { expenseType ->
                val expenseAmount = expensesViewModel.allExpenses[expenseType]?.values?.sum() ?: 0.0
                val percentage = if (totalExpenses > 0) (expenseAmount / totalExpenses) * 100 else 0.0
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    iconsByExpenseType[expenseType]?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "$expenseType Icon",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = expenseType,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        PercentageBar(percentage = percentage / 100.0)
                    }
                    Text(
                        text = "$${"%.2f".format(expenseAmount)}",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }
            }
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
                Spacer(modifier = Modifier.height(5.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(5.dp))
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
