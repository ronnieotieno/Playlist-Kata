package dev.challenge.playlistcodekata.di.modules

import dagger.Module

/**
 *  A [dagger.Module] scoped to the Application class
 */


@Module(
    includes = [
        ViewModelModule::class,
        DataModule::class
    ]
)
class ApplicationModule