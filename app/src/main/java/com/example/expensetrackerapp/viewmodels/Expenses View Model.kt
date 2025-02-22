package com.example.expensetrackerapp.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class ExpensesViewModel: ViewModel() {
    val allExpenses = mutableStateMapOf<String, MutableMap<String, Double>>()

    fun sumExpenses(
    ): Double {
        return allExpenses.values.flatMap { it.values }.sumOf { it }
    }
}