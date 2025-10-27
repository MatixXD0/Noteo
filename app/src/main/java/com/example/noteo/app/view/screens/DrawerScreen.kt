package com.example.noteo.app.view.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.noteo.R

sealed class DrawerScreen(
    @StringRes
    val dTitle: Int,
    val dRoute: String,
    @DrawableRes
    val icon: Int
) : Screen(dTitle, dRoute) {

    object AboutAppScreen : DrawerScreen(
        R.string.screen_name_about_app,
        "about_app_screen",
        R.drawable.baseline_info_outline_24
    )

    object ContactScreen : DrawerScreen(
        R.string.screen_name_contact,
        "contact_screen",
        R.drawable.baseline_contact_mail_24
    )
}

val screensInDrawer = listOf(
    DrawerScreen.AboutAppScreen,
    DrawerScreen.ContactScreen
)


