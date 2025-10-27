package com.example.noteo.app.view.main

import androidx.compose.runtime.Composable
import com.example.noteo.app.viewModel.MainViewModel
import com.example.noteo.ui.theme.DarkTheme
import com.example.noteo.ui.theme.LightTheme

@Composable
fun MainView(mainViewModel: MainViewModel) {
    val currentTheme = mainViewModel.currentTheme.value

    when (currentTheme) {
        MainViewModel.Theme.LIGHT -> {
            LightTheme {
                MainContent(mainViewModel)
            }
        }

        MainViewModel.Theme.DARK -> {
            DarkTheme {
                MainContent(mainViewModel)
            }
        }
    }
}


