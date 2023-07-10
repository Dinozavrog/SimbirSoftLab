package com.example.simbirsoft.di

import com.example.simbirsoft.data.utils.AndroidResourceProvider
import com.example.simbirsoft.data.utils.ResourceProvider
import com.example.simbirsoft.presentation.presenter.CreateNoteViewModel
import com.example.simbirsoft.presentation.presenter.HomeViewModel
import com.example.simbirsoft.presentation.presenter.NoteDetailsViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
    viewModel {
        HomeViewModel(
            get()
        )
    }
    viewModel {
        CreateNoteViewModel(
            get()
        )
    }
    viewModel {
        NoteDetailsViewModel(
            get()
        )
    }
    factory<ResourceProvider> {
        AndroidResourceProvider(get())
    }
}