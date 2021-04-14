package dev.challenge.playlistcodekata.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Object which also act as [androidx.room.Entity] class with table name
 */
@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Int,
    val username: String,
    val isPro: Boolean
)