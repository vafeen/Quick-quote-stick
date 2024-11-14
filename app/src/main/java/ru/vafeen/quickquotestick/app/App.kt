package ru.vafeen.quickquotestick.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.quickquotestick.presentation.di.main.mainPresentationModule

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                mainPresentationModule,
//                mainDomainModule,
//                mainDataModule,
            )
        }
    }
}