package com.joragupra.budinv.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joragupra.budinv.android.api.BookkeepingEntryDto
import com.joragupra.budinv.android.api.LedgerDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerScreen(viewModel: LedgerViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("BudInv Ledger") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val s = state) {
                is LedgerUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is LedgerUiState.Error -> ErrorContent(message = s.message, onRetry = viewModel::loadLedger)
                is LedgerUiState.Success -> LedgerContent(ledger = s.ledger)
            }
        }
    }
}

@Composable
private fun LedgerContent(ledger: LedgerDto) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { SummaryCard(ledger) }
        item {
            Text(
                text = "Entries",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        items(ledger.entries) { entry ->
            EntryRow(entry)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun SummaryCard(ledger: LedgerDto) {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Period: ${ledger.from} → ${ledger.to}", style = MaterialTheme.typography.bodySmall)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Income", color = Color(0xFF2E7D32))
                Text("%.2f".format(ledger.totalIncome), color = Color(0xFF2E7D32))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Expenses", color = Color(0xFFC62828))
                Text("%.2f".format(ledger.totalExpense), color = Color(0xFFC62828))
            }
            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Balance", style = MaterialTheme.typography.titleMedium)
                Text("%.2f".format(ledger.balance), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun EntryRow(entry: BookkeepingEntryDto) {
    val isIncome = entry is BookkeepingEntryDto.Income
    val color = if (isIncome) Color(0xFF2E7D32) else Color(0xFFC62828)
    val label = if (isIncome) "INCOME" else "EXPENSE"

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = color)
            Text(entry.incurredDate, style = MaterialTheme.typography.bodySmall)
            entry.comments?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }
        Text("%.2f".format(entry.amount), color = color)
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Error: $message", style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) {
            Text("Retry")
        }
    }
}
