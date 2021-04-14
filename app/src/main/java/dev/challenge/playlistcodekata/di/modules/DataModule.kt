package dev.challenge.playlistcodekata.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.challenge.playlistcodekata.data.dao.PlayListDao
import dev.challenge.playlistcodekata.data.db.PlayListDataBase
import javax.inject.Singleton

/**
 *  A [dagger.Module] provides the [PlayListDataBase] and [PlayListDao]
 */

@Module
object DataModule {
    @Provides
    @Singleton
    fun providesDB(app: Application): PlayListDataBase {
        return PlayListDataBase.invoke(app.applicationContext)
    }

    @Provides
    @Singleton
    fun providePlayListDao(database: PlayListDataBase): PlayListDao {
        return database.playListDao
    }

}