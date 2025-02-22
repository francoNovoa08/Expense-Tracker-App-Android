package com.example.expensetrackerapp.viewmodels

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel

class IncomeViewModel: ViewModel() {
    val income = mutableDoubleStateOf(0.0)
}