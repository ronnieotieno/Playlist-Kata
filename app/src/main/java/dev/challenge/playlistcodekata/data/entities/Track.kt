package dev.challenge.playlistcodekata.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Track Object which also act as [androidx.room.Entity] class with table name
 */
@Entity(tableName = "tracks_table")
data class Track(
    @PrimaryKey(autoGenerate = true) var trackId: Int? = null,
    val title: String,
    val duration: Long
)