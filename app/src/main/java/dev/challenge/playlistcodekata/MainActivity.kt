package dev.challenge.playlistcodekata

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import dev.challenge.playlistcodekata.viewmodels.MainViewModel
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //All of the following functions can be safely be called from any fragment or activity
        //There is no ui so all of them will be called on Android Test Package as the challenge indicated
        viewModel.apply {
//            insertMultiplePlayListEntry()
//            insertMultipleTracks()
//            insertPlayListEntry()
//            insertTrack()
//            insertUser()
//            removeMultipleTracks()
//            removeSingleTrack()
//            getUserPlayList()
//            countTracksInPlayList()
//            getUserPlayListDuration()

        }

    }
}