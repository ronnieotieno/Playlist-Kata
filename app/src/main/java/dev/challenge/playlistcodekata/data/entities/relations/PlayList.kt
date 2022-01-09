package dev.challenge.playlistcodekata.data.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User

/**
 *[PlayList] comes from the relation between [Track] and [User] as indicated in [PlayListEntry] entity/table
 * [tracks] is a list of [Track] associated with the [User] in the [PlayListEntry]
 */

data class PlayList(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "trackId",
        associateBy = Junction(PlayListEntry::class)
    )
    val tracks: List<Track>
)