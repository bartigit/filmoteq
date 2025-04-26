package com.example.filmoteq.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.filmoteq.R
import com.example.filmoteq.model.Category
import com.example.filmoteq.model.Status

@Composable
fun FilterDialog(
    initialCategory: Category?,
    initialStatus: Status?,
    onDismiss: () -> Unit,
    onApply: (Category?, Status?) -> Unit,
    onClearFilters: () -> Unit
) {
    var tempCategory by remember { mutableStateOf(initialCategory) }
    var tempStatus by remember { mutableStateOf(initialStatus) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.filter_films)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(stringResource(R.string.category_colon), style = MaterialTheme.typography.titleMedium)
                Category.entries.forEach { cat ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                tempCategory = if (tempCategory == cat) null else cat
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (tempCategory == cat),
                            onClick = {
                                tempCategory = if (tempCategory == cat) null else cat
                            }
                        )
                        Text(cat.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }

                Text(stringResource(R.string.status_colon), style = MaterialTheme.typography.titleMedium)
                Status.entries.forEach { st ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                tempStatus = if (tempStatus == st) null else st
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (tempStatus == st),
                            onClick = {
                                tempStatus = if (tempStatus == st) null else st
                            }
                        )
                        Text(st.name, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }

                TextButton(
                    onClick = {
                        onClearFilters()
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.clear_filters))
                }

                TextButton(onClick = { onApply(tempCategory, tempStatus) }) {
                    Text(stringResource(R.string.apply))
                }
            }
        }
    )
}
