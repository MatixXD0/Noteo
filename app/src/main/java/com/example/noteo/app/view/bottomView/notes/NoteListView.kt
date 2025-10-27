package com.example.noteo.app.view.bottomView.notes

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteo.R
import com.example.noteo.app.model.note.Note
import com.example.noteo.app.view.screens.Screen
import com.example.noteo.app.viewModel.NoteViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteListView(
    navController: NavController,
    viewModel: NoteViewModel,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(all = 20.dp),
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(Screen.NoteAddEditScreen.route + "/0L")
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }


        ) {
            val noteList = viewModel.getAllNotes.collectAsState(initial = listOf())
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(noteList.value, key = { note -> note.id }) { note ->

                    //swipe to delete
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                viewModel.deleteNote(note)
                            }
                            true
                        }
                    )


                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                    Color.Red
                                else
                                    Color.Transparent,
                                label = ""
                            )
                            val aligment = Alignment.CenterEnd
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = aligment
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete_icon),
                                    tint = Color.White
                                )
                            }
                        },
                        directions = setOf(
                            DismissDirection.EndToStart
                        ),
                        dismissThresholds = { FractionalThreshold(0.25f) },
                        dismissContent = {
                            Note(note = note) {
                                val id = note.id
                                navController.navigate(Screen.NoteAddEditScreen.route + "/$id")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Note(
    note: Note,
    onClick: () -> Unit
) {
    Card(
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color(note.color)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = note.title,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black)
            Text(text = note.description,
                color = Color.Black)
        }

    }
}