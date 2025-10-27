package com.example.noteo.app.view.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.noteo.app.view.screens.DrawerScreen


@Composable
fun DrawerItems(
    selected: Boolean,
    item: DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val background =
        if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface
    val contentColor =
        if (selected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onBackground

    Card(
        backgroundColor = background,
        contentColor = contentColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                onDrawerItemClicked()
            },
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.dTitle.toString(),
                modifier = Modifier.padding(start = 8.dp,end = 8.dp, top = 4.dp),
                tint = contentColor,
            )
            Text(
                text =  stringResource(id = item.dTitle),
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

