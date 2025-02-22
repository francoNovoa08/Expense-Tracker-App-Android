package com.example.expensetrackerapp.uiElements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.expensetrackerapp.ui.theme.ExpenseTrackerAppTheme
import com.example.expensetrackerapp.uiScreens.ExpenseScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRow() {
    val tabItems = listOf(
        "Summary", "Housing", "Food", "Transportation", "Other"
    )
    ExpenseTrackerAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            val pagerState = rememberPagerState { tabItems.size }
            val scope = rememberCoroutineScope()
            LaunchedEffect(pagerState.currentPage) {
                selectedTabIndex = pagerState.currentPage
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Expense Tracker",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(15.dp)
                )
                androidx.compose.material3.TabRow(
                    selectedTabIndex = selectedTabIndex
                ) {
                    tabItems.forEachIndexed { index, item ->
                        Tab(
                            selected = (index == selectedTabIndex),
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                                selectedTabIndex = index
                            },
                            text = {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    userScrollEnabled = true
                ) { index ->
                    ExpenseScreen(tab = tabItems[index])
                }
            }
        }
    }
}