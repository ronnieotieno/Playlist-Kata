package dev.challenge.playlistcodekata.data.repository

import dev.challenge.playlistcodekata.data.dao.PlayListDao
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User
import dev.challenge.playlistcodekata.viewmodels.MainViewModel
import javax.inject.Inject

/**
 * The app repository receiving the data from [PlayListDao]
 *
 * The service [PlayListDao] is injected by [Dagger 2](https://dagger.dev/dev-guide)
 *
 * All the functions are replicated and documented in [MainViewModel]
 */
class PlayListRepository @Inject constructor(private val playListDao: PlayListDao) {

    suspend fun insertUser(user: User) = playListDao.insertUser(user)

    suspend fun insertTrack(track: Track) = playListDao.insertTrack(track)

    suspend fun getTracks() = playListDao.getTracks()

    suspend fun getUser(id: Int) = playListDao.getUser(id)

    suspend fun insertMultipleTracks(tracks: List<Track>) = playListDao.insertTracks(tracks)

    suspend fun insertPlayListEntry(playListEntry: PlayListEntry) =
        playListDao.insertPlayListEntry(playListEntry)

    suspend fun insertMultiplePlayListEntry(playListEntries: List<PlayListEntry>) =
        playListDao.insertMultiplePlayListEntry(playListEntries)

    suspend fun countTracksInPlayList(userId: Int) = playListDao.countTracksInPlayList(userId)

    suspend fun getPlayList(userId: Int) = playListDao.getPlayList(userId)

    suspend fun removeSingleTrackFromPlayList(playListEntry: PlayListEntry) =
        playListDao.deletePlayListEntry(playListEntry)

    suspend fun removeMultipleTracksFromPlayList(playListEntries: List<PlayListEntry>) =
        playListDao.deleteMultiplePlayListEntry(playListEntries)

    suspend fun getTracksById(id: Int) = playListDao.getTracksById(id)

}