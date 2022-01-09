package dev.challenge.playlistcodekata

import androidx.room.Insert
import androidx.room.Room
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import dev.challenge.playlistcodekata.data.dao.PlayListDao
import dev.challenge.playlistcodekata.data.db.PlayListDataBase
import dev.challenge.playlistcodekata.data.entities.PlayListEntry
import dev.challenge.playlistcodekata.data.entities.relations.PlayList
import dev.challenge.playlistcodekata.data.repository.PlayListRepository
import dev.challenge.playlistcodekata.viewmodels.MainViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class MainViewModelTest {
    private lateinit var repository: PlayListRepository
    private lateinit var database: PlayListDataBase
    private lateinit var viewmodel: MainViewModel
    private lateinit var playListDao: PlayListDao

//    @Rule
//    @JvmField
//    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    //get Activity and make the viewmodel
    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        //create a "disposable" database
        database = Room.inMemoryDatabaseBuilder(context, PlayListDataBase::class.java).build()

        playListDao = database.playListDao
        repository = PlayListRepository(playListDao)


        viewmodel = MainViewModel(
            repository
        )

        //populate db with data to be used
        runBlocking {
            viewmodel.insertUser(userBerlin)
            viewmodel.insertUser(userLa)
            viewmodel.insertUser(userNyc)
            viewmodel.insertUser(userLondon)
            viewmodel.insertMultipleTracks(generateManyTracks())

        }

    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun inserts_user_into_db_and_returns_1() {
        runBlocking {
            val rowsAffected = viewmodel.insertUser(userBerlin)
            assertThat(rowsAffected, CoreMatchers.equalTo(1L))
        }
    }

    @Test
    fun inserts_track_into_db_and_returns_1() {
        runBlocking {
            val rowsAffected = viewmodel.insertTrack(trackOne)
            assertThat(rowsAffected, CoreMatchers.equalTo(1L))
        }
    }

    @Test
    fun inserts_playListEntry_into_db_returns_success() {
        runBlocking {
            val user = viewmodel.getUserById(1)

            val task = viewmodel.insertPlayListEntry(PlayListEntry(user.userId, 1), user)
            assertThat(task, CoreMatchers.equalTo(TASK_SUCCESSFUL))
        }
    }

    @Test
    fun inserts_multiple_tracks_into_db_and_returns_300() {
        runBlocking {
            val rowList = viewmodel.insertMultipleTracks(generateManyTracks())

            assertThat(rowList.size, CoreMatchers.equalTo(300))
        }
    }

    @Test
    fun inserts_multiple_playListEntries_into_db_userPro_return_error() {
        runBlocking {

            //Trying to insert more than 200 which is the limit for pro user

            val user = viewmodel.getUserById(1)

            //generates 300 playListEntries
            val playListEntryList = generateManyPlayListEntries(user.userId, viewmodel.getTracks())

            //try inserting all 300
            val result = viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            assertThat(result, CoreMatchers.equalTo(NOTIFY_PRO_USER_LIMIT))
        }
    }

    @Test
    fun inserts_multiple_playListEntries_into_db_userPro_return_success() {
        runBlocking {

            //Inserting 199 tracks to playlist at once

            val user = viewmodel.getUserById(1)

            //generates 199 playListEntries
            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 199))

            val result = viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            assertThat(result, CoreMatchers.equalTo(TASK_SUCCESSFUL))

        }
    }

    @Test
    fun inserts_playListEntry_into_db_userPro_return_error() {
        runBlocking {

            //Trying to add the tracks to playlist after reaching the limit

            //first add 200 tracks in playlist, to make it reach limit
            val user = viewmodel.getUserById(1)
            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 200))

            viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            //Then try adding one more track
            val task = viewmodel.insertPlayListEntry(PlayListEntry(user.userId, 1), user)

            assertThat(task, CoreMatchers.equalTo(NOTIFY_PRO_USER_LIMIT))
        }
    }

    @Test
    fun inserts_multiple_playListEntries_into_db_userNotPro_return_error() {
        runBlocking {

            //Trying to insert more than 100 which is the limit for pro user
            val user = viewmodel.getUserById(2)

            val playListEntryList = generateManyPlayListEntries(user.userId, viewmodel.getTracks())

            val result = viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            assertThat(result, CoreMatchers.equalTo(NOTIFY_USER_LIMIT))
        }
    }

    @Test
    fun inserts_multiple_playListEntries_into_db_userNotPro_return_success() {
        runBlocking {

            //Inserting 99 tracks to playlist at once
            val user = viewmodel.getUserById(2)

            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 99))

            val result = viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            assertThat(result, CoreMatchers.equalTo(TASK_SUCCESSFUL))
        }
    }

    @Test
    fun inserts_playListEntry_into_db_userNotPro_return_error() {
        runBlocking {

            //first add 100 tracks in playlist, to make it reach limit
            val user = viewmodel.getUserById(2)
            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 100))

            viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            //Then try adding one more track
            val task = viewmodel.insertPlayListEntry(PlayListEntry(user.userId, 1), user)

            assertThat(task, CoreMatchers.equalTo(NOTIFY_USER_LIMIT))
        }
    }

    @Test
    fun remove_single_track_from_db_returns_1() {
        runBlocking {

            val user = viewmodel.getUserById(2)
            val playListEntry = PlayListEntry(user.userId, 1)
            viewmodel.insertPlayListEntry(playListEntry, user)
            val rowAffected = viewmodel.removeSingleTrack(playListEntry)

            assertThat(rowAffected, CoreMatchers.equalTo(1))
        }
    }

    @Test
    fun remove_multiple_tracks_from_db_returns_25() {
        runBlocking {

            //adding 50 playlisttentries and removing 25
            val user = viewmodel.getUserById(2)
            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 50))

            viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            val rowAffected = viewmodel.removeMultipleTracks(playListEntryList.subList(0, 25))

            assertThat(rowAffected, CoreMatchers.equalTo(25))
        }
    }

    @Test
    fun count_tracks_in_playlist_returns_51() {
        runBlocking {

            //add 50 by multiples then add a single one to make the count to be 51
            val user = viewmodel.getUserById(2)
            val playListEntryList =
                generateManyPlayListEntries(user.userId, viewmodel.getTracks().subList(0, 50))

            viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            val playListEntry = PlayListEntry(user.userId, 101)
            viewmodel.insertPlayListEntry(playListEntry, user)

            val count = viewmodel.countTracksInPlayList(user)

            assertThat(count, CoreMatchers.equalTo(51))
        }
    }

    @Test
    fun get_user_playlist_returns_inserted_playlist() {
        runBlocking {

            //insert and compare
            val user = viewmodel.getUserById(2)
            val tracks = viewmodel.getTracks().subList(0, 50)
            val playListEntryList =
                generateManyPlayListEntries(user.userId, tracks)

            viewmodel.insertMultiplePlayListEntry(playListEntryList, user)

            val expectedPlayList = PlayList(user, tracks)

            val returnedPlayList = viewmodel.getUserPlayList(user)

            assertThat(
                returnedPlayList,
                CoreMatchers.equalTo(expectedPlayList)
            )

        }
    }

    @Test
    fun get_user_playlist_duration_returns_inserted_playlist_duration() {
        runBlocking {

            val user = viewmodel.getUserById(2)

            val tracks = listOf(trackOne, trackTwo, trackThree, trackFour)
            viewmodel.insertMultipleTracks(tracks)

            val expectedDuration = tracks.sumByLong { it.duration }

            val playListEntries = tracks.map { PlayListEntry(user.userId, it.trackId!!) }

            viewmodel.insertMultiplePlayListEntry(playListEntries, user)

            val duration = viewmodel.getUserPlayListDuration(user)

            assertThat(
                duration,
                CoreMatchers.equalTo(expectedDuration)
            )
        }
    }

}