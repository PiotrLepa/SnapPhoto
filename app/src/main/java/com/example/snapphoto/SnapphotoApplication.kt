package com.example.snapphoto

import android.app.Application
import com.example.snapphoto.data.repository.SnapphotoRepository
import com.example.snapphoto.data.repository.SnapphotoRepositoryImpl
import com.example.snapphoto.ui.authentication.registration.RegistrationFragmentNavigator
import com.example.snapphoto.ui.authentication.registration.RegistrationViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.*
import timber.log.Timber

class SnapphotoApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind<SnapphotoRepository>() with singleton { SnapphotoRepositoryImpl()}
        bind() from factory { fragmentNavigator: RegistrationFragmentNavigator -> RegistrationViewModelFactory(fragmentNavigator, instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("planted")
        }
    }
}