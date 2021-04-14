package dev.challenge.playlistcodekata.di

import android.app.Application

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.challenge.playlistcodekata.PlayListApp
import dev.challenge.playlistcodekata.di.modules.ApplicationModule
import dev.challenge.playlistcodekata.di.modules.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        MainActivityModule::class
    ]
)
/**
 * Generates [dagger.Component] which is install in the [Application] class.
 */
interface ApplicationComponent : AndroidInjector<PlayListApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(playListApp: PlayListApp)
}
