package de.tdsoftware.moviesharing

import de.tdsoftware.moviesharing.data.models.PlaylistApp
import de.tdsoftware.moviesharing.data.models.VideoApp

/**
 * sample data to check if adapter works with the models that are created to hold lateron data
 */

object Sample {

    val videoOne = VideoApp("abcd","Test 1", "This is test1 item!","Movie",0f,false, "")
    val videoTwo = VideoApp("abcde","Test 2", "This is test2 item!","Movie",0f,false, "")
    val videoThree = VideoApp("abcdfg","Test 23", "This is test3 item!","Movie",0f,false, "")
    val videoFour = VideoApp("abdf","Test 4", "This is test4 item!","Movie",0f,false, "")
    val videoFive = VideoApp("adsa","Test 5", "This is test5 item!","Movie",0f,false, "")


    val playlistOne = PlaylistApp("pl1","Playlist 1", arrayListOf(videoOne, videoTwo))
    val playlistTwo = PlaylistApp("pl2", "Playlist 2", arrayListOf(videoThree, videoFour, videoFive))

    val playlistFavorite = PlaylistApp("favorite", "Favorites", arrayListOf())

    val playlistSampleList = arrayListOf(playlistOne, playlistTwo)
}