package dev.challenge.playlistcodekata.viewmodels

import androidx.lifecycle.ViewModel
import dev.challenge.playlistcodekata.*
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.Track
import dev.challenge.playlistcodekata.data.entities.User
import dev.challenge.playlistcodekata.data.entities.relations.PlayList
import dev.challenge.playlistcodekata.data.repository.PlayListRepository
import javax.inject.Inject

/**
 * A [ViewModel] that prepares data gotten from the repository
 */
class MainViewModel @Inject constructor(private val repository: PlayListRepository) : ViewModel() {

    /**
     * returns [Long] with number of rows Affected
     */
    suspend fun insertUser(user: User) = repository.insertUser(user)

    /**
     * returns [User] with the [id] as [User.userId]
     */
    suspend fun getUserById(id: Int) = repository.getUser(id)

    /**
     * returns list of [Track] present in the Db
     */
    suspend fun getTracks() = repository.getTracks()

    /**
     * returns [Track] with the [id] as [Track.trackId]
     */
    suspend fun getTracksById(id: Int) = repository.getTracksById(id)

    /**
     * First check the number of tracks in the playList of the user by calling [countTracksInPlayList] passing [user]
     *  then if the count is 100, meaning that the list is full if the [user] is a regular user then return [NOTIFY_USER_LIMIT],
     *  if the count is 200, meaning that the list is full if the [user] is a pro user then return [NOTIFY_PRO_USER_LIMIT],
     * if all the count is less than 100 or 200 for regular and pro respectively, then the [playListEntry] is inserted
     */
    suspend fun insertPlayListEntry(playListEntry: PlayListEntry, user: User): String {
        val currentCount = countTracksInPlayList(user)

        currentCount.apply {
            if (user.isPro && this >= 200) {
                return NOTIFY_PRO_USER_LIMIT
            }

            if (!user.isPro && this >= 100) {
                return NOTIFY_USER_LIMIT
            }
        }

        return if (repository.insertPlayListEntry(playListEntry) < 0) NOTIFY_ERROR_OCCURRED else TASK_SUCCESSFUL
    }

    /**
     * First check the number of tracks in the playList of the user by calling [countTracksInPlayList] passing [user]
     *
     * Then add the size of the list of [playListEntries] to the count.
     *
     * If the addition is more than 100, meaning that the list is full if the [user] is a regular user then return [NOTIFY_USER_LIMIT],
     *
     * If the addition is more than  200, meaning that the list is full if the [user] is a pro user then return [NOTIFY_PRO_USER_LIMIT],
     *
     * If all the addition is less than or equal to 100 or 200 for regular and pro respectively, then the [playListEntries] is inserted
     */
    suspend fun insertMultiplePlayListEntry(
        playListEntries: List<PlayListEntry>,
        user: User
    ): String {
        val currentCount = countTracksInPlayList(user)

        val cumulative = playListEntries.size + currentCount

        if (user.isPro && cumulative > 200) {
            return NOTIFY_PRO_USER_LIMIT
        }

        if (!user.isPro && cumulative > 100) {
            return NOTIFY_USER_LIMIT
        }

        return if (repository.insertMultiplePlayListEntry(playListEntries)
                .isEmpty()
        ) NOTIFY_ERROR_OCCURRED else TASK_SUCCESSFUL
    }

    /**
     * returns [Long] with the number of rows Affected, remove the db playListEntry matching [playListEntry]
     */
    suspend fun removeSingleTrack(playListEntry: PlayListEntry) =
        repository.removeSingleTrackFromPlayList(playListEntry)

    /**
     * returns list of [Long] with the number of rows Affected, remove the db playListEntry matching [playListEntries]
     */
    suspend fun removeMultipleTracks(playListEntries: List<PlayListEntry>) =
        repository.removeMultipleTracksFromPlayList(playListEntries)


    /**
     * returns [Int] with the number of tracks in the playList of the [User]
     */
    suspend fun countTracksInPlayList(user: User) =
        user.userId.let { repository.countTracksInPlayList(it) }

    /**
     * returns [PlayList] of the [User]
     */
    suspend fun getUserPlayList(user: User) = user.userId.let { repository.getPlayList(it) }

    /**
     * Adds the duration of all tracks of the [user] [PlayList]
     *  returns [Long] with the addition
     */
    suspend fun getUserPlayListDuration(user: User) =
        repository.getPlayList(user.userId).tracks.sumByLong { it.duration }

    /**
     * returns [Long] with the number of rows affected
     */
    suspend fun insertTrack(track: Track) = repository.insertTrack(track)

    /**
     * returns list of [Long] with the number of rows affected
     */
    suspend fun insertMultipleTracks(tracks: List<Track>) = repository.insertMultipleTracks(tracks)


}