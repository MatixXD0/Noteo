package com.example.noteo.app.view.main

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.noteo.app.view.screens.BottomScreen
import com.example.noteo.app.view.screens.DrawerScreen
import com.example.noteo.app.view.screens.Screen

@Composable
fun BottomBar(
    currentScreen: Screen,
    currentRoute: String?,
    screensInBottom: List<BottomScreen>,
    controller: NavController,
) {
    if (currentScreen is DrawerScreen || currentScreen == BottomScreen.NotesListScreen) {
        BottomNavigation(
            Modifier.wrapContentSize(),
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            screensInBottom.forEach { item ->
                val isSelected = currentRoute == item.bRoute
                val tint = if (isSelected) Color.White else Color.Black
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        controller.navigate(item.bRoute)
                    },
                    icon = {
                        Icon(
                            tint = tint,
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.bTitle)
                        )
                    },
                    label = {
                        Text(
                            stringResource(id = item.bTitle),
                            color = tint
                        )
                    },
                )
            }
        }
    }
}