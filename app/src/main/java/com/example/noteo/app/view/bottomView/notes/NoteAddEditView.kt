package com.example.noteo.app.view.bottomView.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteo.R
import com.example.noteo.app.model.note.Note
import com.example.noteo.app.viewModel.NoteViewModel
import com.example.noteo.ui.theme.noteColorDefault
import com.example.noteo.ui.theme.noteColorLightBlue
import com.example.noteo.ui.theme.noteColorLightGreen
import com.example.noteo.ui.theme.noteColorLightRed
import com.example.noteo.ui.theme.noteColorLightViolet
import com.example.noteo.ui.theme.noteColorLightYellow
import kotlinx.coroutines.launch

@Composable
fun NoteAddEditView(
    id: Long,
    viewModel: NoteViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val scrollState = rememberScrollState()


    if (id != 0L) {
        val note = viewModel.getNoteById(id).collectAsState(initial = Note(0L, "", ""))
        viewModel.noteNameState = note.value.title
        viewModel.noteDescriptionState = note.value.description
        viewModel.noteColorState = note.value.color
    } else {
        viewModel.noteNameState = ""
        viewModel.noteDescriptionState = ""
        viewModel.noteColorState = noteColorDefault.toArgb()
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                Spacer(modifier = Modifier.height(10.dp))
                NoteTextField(
                    label = stringResource(R.string.note_title),
                    value = viewModel.noteNameState,
                    onValueChanged = {
                        viewModel.onNoteNameChanged(it)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                NoteTextField(
                    label = stringResource(R.string.note_description),
                    value = viewModel.noteDescriptionState,
                    onValueChanged = {
                        viewModel.onNoteDescriptionChanged(it)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))

                val availableColors = listOf(
                    noteColorLightRed.toArgb(),
                    noteColorLightGreen.toArgb(),
                    noteColorLightBlue.toArgb(),
                    noteColorLightYellow.toArgb(),
                    noteColorLightViolet.toArgb()
                )
                Text(
                    text = stringResource(R.string.note_color),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    availableColors.forEach { colorInt ->
                        val isSelected = (viewModel.noteColorState == colorInt)

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) Color.Black else Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                )
                                .background(Color(colorInt), shape = MaterialTheme.shapes.small)
                                .clickable {
                                    viewModel.onNoteColorChanged(colorInt)
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = {
                        // Sprawdzenie, czy pola nie są puste
                        if (viewModel.noteNameState.isBlank()) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.note_snack_enter_the_note_name))
                            }
                            return@Button
                        }

                        if (viewModel.noteDescriptionState.isBlank()) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.note_snack_enter_a_description_of_the_note))
                            }
                            return@Button
                        }

                        // Jeśli oba pola są wypełnione, kontynuuj dodawanie/aktualizację
                        if (id != 0L) {
                            // Aktualizacja elementu
                            viewModel.updateNote(
                                Note(
                                    id = id,
                                    title = viewModel.noteNameState.trim(),
                                    description = viewModel.noteDescriptionState.trim(),
                                    color = viewModel.noteColorState
                                )
                            )
                        } else {
                            // Dodanie nowego elementu
                            viewModel.addNote(
                                Note(
                                    title = viewModel.noteNameState.trim(),
                                    description = viewModel.noteDescriptionState.trim(),
                                    color = viewModel.noteColorState
                                )
                            )
                        }
                        navController.navigateUp()
                    }
                )
                {
                    Text(
                        text = if (id != 0L)
                            stringResource(R.string.note_update_button)
                        else
                            stringResource(R.string.note_add_button),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Composable
fun NoteTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}


@Preview(showBackground = true)
@Composable
fun NoteTextFieldPrev() {
    NoteTextField(label = "test", value = "test 2", onValueChanged = {})
}
