package dev.challenge.playlistcodekata.data.entities

import androidx.room.Entity
import androidx.room.RoomDatabase

/**
 * Using [RoomDatabase] many-to-many relationship see [Room Docs](https://developer.android.com/training/data-storage/room/relationships)
 *
 *Create the cross reference entity/table with [User.userId] and [Track.trackId]
 */

@Entity(primaryKeys = ["userId", "trackId"], tableName = "play_list_table")
data class PlayListEntry(
    val userId: Int,
    val trackId: Int
)