package dev.challenge.playlistcodekata.di.modules


import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.challenge.playlistcodekata.MainActivity

/**
 * A [dagger.Module] to provide the the [MainActivity]
 */
@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
