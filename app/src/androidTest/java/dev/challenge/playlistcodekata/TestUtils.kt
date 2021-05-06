package dev.challenge.playlistcodekata

import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User


//Generate sample users tracks to be inserted in db for test

val userBerlin = User(userId = 1, username = "berlin", isPro = true)
val userLa = User(userId = 2, username = "losAngeles", isPro = false)
val userNyc = User(userId = 3, username = "nyc", isPro = false)
val userLondon = User(userId = 4, username = "london", isPro = true)

val trackOne = Track(title = "Love", duration = 1000L, trackId = 1)
val trackTwo = Track(title = "Happiness", duration = 2000L, trackId = 2)
val trackThree = Track(title = "Peace", duration = 3000L, trackId = 3)
val trackFour = Track(title = "Success", duration = 4000L, trackId = 4)

fun generateManyTracks() = (1..300).map { Track(title = "Random$it", duration = 100L) }

//Can be more than 200
//TrackId is ever present as the passed list of tracks are from the db
fun generateManyPlayListEntries(userId: Int, tracks: List<Track>) =
    tracks.indices.map { index ->
        PlayListEntry(
            userId = userId,
            trackId = tracks[index].trackId!!
        )
    }