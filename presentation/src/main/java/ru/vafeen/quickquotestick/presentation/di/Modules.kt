package ru.vafeen.quickquotestick.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.quickquotestick.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.quickquotestick.presentation.components.viewModels.MainScreenViewModel
import ru.vafeen.quickquotestick.presentation.components.viewModels.SettingsScreenViewModel


internal val koinViewModelDIModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}