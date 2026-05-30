package com.joragupra.budinv.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate

enum class EntryType { Income, Expense }

internal fun parseAmount(input: String): Double? {
    val trimmed = input.trim()
    if (trimmed.isEmpty()) return null
    val lastDot = trimmed.lastIndexOf('.')
    val lastComma = trimmed.lastIndexOf(',')
    val normalized = when {
        lastComma > lastDot -> trimmed.replace(".", "").replace(',', '.')
        lastDot > lastComma -> trimmed.replace(",", "")
        else -> trimmed
    }
    val value = normalized.toDoubleOrNull() ?: return null
    return if (value.isFinite() && value > 0) value else null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntrySheet(onDismiss: () -> Unit, onConfirm: (EntryType, Double, String?) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        AddEntryForm(onDismiss = onDismiss, onConfirm = onConfirm)
    }
}

@Composable
internal fun AddEntryForm(onDismiss: () -> Unit, onConfirm: (EntryType, Double, String?) -> Unit) {
    var selectedType by remember { mutableStateOf(EntryType.Income) }
    var amountText by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }

    val amount = parseAmount(amountText)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Add Entry", style = MaterialTheme.typography.titleLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EntryType.entries.forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { selectedType = type },
                    label = { Text(type.name) },
                )
            }
        }

        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text("Amount") },
            isError = amountText.isNotEmpty() && amount == null,
            supportingText = {
                if (amountText.isNotEmpty() && amount == null) Text("Enter a positive amount, e.g. 1234.56")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = comments,
            onValueChange = { comments = it },
            label = { Text("Comments (optional)") },
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = onDismiss) { Text("Cancel") }
            Button(
                onClick = { if (amount != null) onConfirm(selectedType, amount, comments.ifBlank { null }) },
                enabled = amount != null,
                modifier = Modifier.padding(start = 8.dp),
            ) { Text("Save") }
        }
    }
}
