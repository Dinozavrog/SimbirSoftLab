package com.example.simbirsoft.presentation.navigation

import androidx.annotation.StringRes
import com.example.simbirsoft.R

sealed class Screen (
    val route: String,
    @StringRes
    val name: Int,
) {
    object Home: Screen(
        route = "home",
        name = R.string.screen_home,
    )
    object AddNote: Screen(
        route = "add",
        name = R.string.screen_add,
    )
    object Details: Screen(
        route = "details/{mangaId}",
        name = R.string.screen_details,
    )
}