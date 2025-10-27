package com.example.noteo.app.view.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteo.R
import com.example.noteo.app.view.screens.Screen


@Composable
fun MoreBottomSheet(
    navController: NavController,
) {
    Surface(
        modifier = Modifier
            .height(125.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(125.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.SettingsScreen.route)
                        },
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp),
                        painter = painterResource(id = R.drawable.baseline_settings_24),
                        contentDescription = stringResource(R.string.screen_name_settings),
                        tint = Color.White

                    )
                    Text(
                        stringResource(R.string.screen_name_settings),
                        fontSize = 20.sp,
                        color = Color.White

                    )
                }
            }
        }
    }
}
