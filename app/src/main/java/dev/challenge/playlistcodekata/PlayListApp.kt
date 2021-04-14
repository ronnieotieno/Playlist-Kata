package dev.challenge.playlistcodekata

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.challenge.playlistcodekata.di.DaggerApplicationComponent

/**
 * The application extends [DaggerApplication] which provide the necessary codes for the Dagger initialization, less boiler plate code.
 */
class PlayListApp : DaggerApplication() {
    private val applicationInjector = DaggerApplicationComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector
}
