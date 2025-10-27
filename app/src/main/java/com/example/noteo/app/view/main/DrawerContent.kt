package com.example.noteo.app.view.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteo.app.view.drawer.DrawerItems
import com.example.noteo.app.view.screens.DrawerScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    currentRoute: String?,
    screensInDrawer: List<DrawerScreen>,
    controller: NavController,
    title: MutableState<Int>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            Modifier.padding(16.dp)
        ) {
            items(screensInDrawer) { item ->
                DrawerItems(
                    selected = currentRoute == item.dRoute,
                    item = item
                ) {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    controller.navigate(item.dRoute)
                    title.value = item.dTitle
                }
            }
        }
    }
}
