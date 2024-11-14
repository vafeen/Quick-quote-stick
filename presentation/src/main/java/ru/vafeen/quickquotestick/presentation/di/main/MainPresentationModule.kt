package ru.vafeen.quickquotestick.presentation.di.main

import org.koin.dsl.module
import ru.vafeen.quickquotestick.presentation.di.koinViewModelDIModule

val mainPresentationModule = module {
    includes(koinViewModelDIModule)
}