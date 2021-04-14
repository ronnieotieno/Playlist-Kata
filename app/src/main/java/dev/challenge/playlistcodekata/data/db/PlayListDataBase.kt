package dev.challenge.playlistcodekata.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.challenge.playlistcodekata.data.dao.PlayListDao
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User


/**
 * Build on top of [SQLiteDatabase], see [Room](https://developer.android.com/training/data-storage/room)
 */

//Entities that are used
@Database(
    entities = [PlayListEntry::class, Track::class, User::class],
    version = 1, exportSchema = false
)
abstract class PlayListDataBase : RoomDatabase() {


    abstract val playListDao: PlayListDao

    //Room should only be initiated once, marked volatile to be thread safe.
    companion object {
        @Volatile
        private var instance: PlayListDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(
                    context
                ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlayListDataBase::class.java,
                "app_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}