package com.example.simbirsoft.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.simbirsoft.domain.usecase.AddNotesFromJsonUseCase
import com.example.simbirsoft.presentation.navigation.NoteNavHost
import com.example.simbirsoft.ui.theme.SimbirSoftTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val addNotesFromJsonUseCase: AddNotesFromJsonUseCase by inject()
        val isFirstLaunch = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            .getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            lifecycleScope.launch {
                addNotesFromJsonUseCase.invoke()
            }
            getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstLaunch", false)
                .apply()
        }
        super.onCreate(savedInstanceState)
        setContent {
            SimbirSoftTheme {
                NoteNavHost()
            }
        }
    }
}