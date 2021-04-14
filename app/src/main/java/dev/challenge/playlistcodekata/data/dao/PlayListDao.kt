package dev.challenge.playlistcodekata.data.dao

import android.database.sqlite.SQLiteDatabase
import androidx.room.*
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User
import dev.challenge.playlistcodekata.data.entities.relations.PlayList

/**
 * Data Access Object, interacts to db and exposes data to the Repository
 *
 * Consist of [RoomDatabase] annotations as well raw [SQLiteDatabase] queries
 *
 * See more [Room Dao](https://developer.android.com/training/data-storage/room/accessing-data)
 */
@Dao
interface PlayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayListEntry(playListEntry: PlayListEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiplePlayListEntry(playListEntries: List<PlayListEntry>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: Track): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<Track>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users_table WHERE userId=:id")
    suspend fun getUser(id: Int): User

    @Query("SELECT * FROM tracks_table WHERE trackId=:id")
    suspend fun getTracksById(id: Int): Track

    @Delete
    suspend fun deletePlayListEntry(playListEntry: PlayListEntry): Int

    @Delete
    suspend fun deleteMultiplePlayListEntry(playListEntries: List<PlayListEntry>): Int

    //Count the tracks
    @Query("SELECT COUNT(trackId) FROM play_list_table WHERE userId=:userId")
    suspend fun countTracksInPlayList(userId: Int): Int

    /**
     * Using relation outlined on [PlayList], the Query returns the [PlayList] Object
     */
    @Transaction
    @Query("SELECT * FROM users_table WHERE userId=:userId LIMIT 1")
    suspend fun getPlayList(userId: Int): PlayList


    @Query("SELECT * FROM tracks_table")
    suspend fun getTracks(): List<Track>

}