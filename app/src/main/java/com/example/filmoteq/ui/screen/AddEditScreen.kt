package com.example.filmoteq.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.filmoteq.R
import com.example.filmoteq.model.Category
import com.example.filmoteq.model.Film
import com.example.filmoteq.model.Status
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFilmScreen(
    film: Film?,
    onSave: (Film) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(film?.title ?: "") }
    var releaseDate by remember { mutableStateOf(film?.releaseDate ?: LocalDate.now()) }
    var category by remember { mutableStateOf(film?.category ?: Category.Akcji) }
    var status by remember { mutableStateOf(film?.status ?: Status.Nieobejrzany) }
    var rating by remember { mutableStateOf(film?.rating?.toString() ?: "") }
    var posterUri by remember { mutableStateOf<Uri?>(film?.posterUri) }
    var showDatePicker by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { posterUri = it }

    val today = LocalDate.now()
    val maxDate = today.plusYears(2)
    val maxDateMillis = maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (film == null) stringResource(R.string.add_film) else stringResource(R.string.edit_film)) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier
                    .size(400.dp)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (posterUri != null) {
                    AsyncImage(model = posterUri, contentDescription = stringResource(R.string.poster))
                } else {
                    Text(
                        stringResource(R.string.tap_to_add_photo),
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            OutlinedTextField(
                value = title,
                onValueChange = { title = it.trim() },
                label = { Text(stringResource(R.string.title)) },
                isError = title.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(onClick = { showDatePicker = true }) {
                Text(stringResource(R.string.release_date, releaseDate.format(DateTimeFormatter.ISO_DATE)))
            }
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = releaseDate
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli(),
                    selectableDates = object : SelectableDates {
                        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                            return utcTimeMillis <= maxDateMillis
                        }
                    }
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                releaseDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            showDatePicker = false
                        }) { Text(stringResource(R.string.ok)) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) { Text(stringResource(R.string.cancel)) }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            Text(stringResource(R.string.category_colon))
            Category.entries.forEach { cat ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = cat == category, onClick = { category = cat })
                    Text(cat.name)
                }
            }
            Text(stringResource(R.string.status_colon))
            Status.entries.forEach { st ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = st == status, onClick = { status = st })
                    Text(st.name)
                }
            }
            if (status == Status.Obejrzany) {
                OutlinedTextField(
                    value = rating,
                    onValueChange = { rating = it.filter(Char::isDigit) },
                    label = { Text(stringResource(R.string.rating_0_10)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = rating.toIntOrNull() !in 0..10,
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (rating.isNotEmpty() && rating.toIntOrNull() !in 1..10) {
                            Text(stringResource(R.string.enter_rating_1_10))
                        }
                    }
                )
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            Film(
                                id = film?.id ?: UUID.randomUUID(),
                                title = title,
                                releaseDate = releaseDate,
                                category = category,
                                status = status,
                                rating = rating.toIntOrNull(),
                                posterUri = posterUri
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() &&
                        (status != Status.Obejrzany || (rating.toIntOrNull() in 1..10))
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
