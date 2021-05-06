
# SoundCloud Kata

An Android project creating, altering and displaying users playlist built with MVVM pattern as well as Architecture Components.

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Prerequisite](#prerequisite)
- [The App](#theapp)
- [Architecture](#architecture)
- [Testing](#testing)
- [Libraries](#libraries)

## Prerequisite

This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

## The App
The app has one Activity that only act as entry point, no ui built. The viewmodel has the functions that communicates which repository which in turn communicates to Dao to get/set data to the Database. From my understanding of the challenge I decided to go with many to many relationship.

 As defined on [Room Doc](https://developer.android.com/training/data-storage/room/relationships)  a many-to-many relationship between two entities is a relationship where each instance of the parent entity corresponds to zero or more instances of the child entity, and vice-versa. 

In our case the the tracks can be part of many playslist and playlist may contain many songs. Inorder to achieve this, a playListEntry data class is needed. 

It has to have two ids, that of the user plus that of the track. These two combined act as primary key under the hood. Then define a relationship(The playList)  with UserId and TrackId as parent  and child column respectively and associate by junction(PlayListEntry) entity. This return the User and the list of tracks. Query the User table where userId matches the provided id and limit to 1 and return a single playList - which contains the user and the list of tracks in the playlist.

    // The entry
           @Entity(primaryKeys = ["userId", "trackId"], tableName =     "play_list_table")  
                 data class PlayListEntry(  
                val userId: Int,  
                val trackId: Int  
            )
            
    //The playlist relation
    
    data class PlayList(
     @Embedded val user: User,  
     @Relation(  
        parentColumn = "userId",  
     entityColumn = "trackId",  
     associateBy = Junction(PlayListEntry::class)  
    )  
    val tracks: List<Track>  
    )

This can be achieved with raw sqlite too but since the project uses the jetpack components, Room proved to be the nice one to use.

## Architecture
The project is built using the MVVM architectural pattern and make heavy use of a couple of Android Jetpack components. Mvvm allows for the separation of concern which also makes testing easier. 

## Testing
All tests are under the Android Test package. All the tests are run using Junit.
 
## Libraries

Libraries used in the whole application are:

- [Jetpack](https://developer.android.com/jetpack)🚀
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
 - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines
-  [Dagger2](https://dagger.dev/dev-guide/) - Used for Dependency injection