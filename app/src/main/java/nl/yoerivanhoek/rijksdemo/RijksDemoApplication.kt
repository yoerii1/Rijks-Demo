package nl.yoerivanhoek.rijksdemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RijksDemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RijksDemoApplication)
            modules(artModule)
        }
    }
}