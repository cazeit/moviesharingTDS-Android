package de.tdsoftware.moviesharing

import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie

/**
 * sample data to check if adapter works with the models that are created to hold lateron data
 */

object Sample {

    val movieOne = Movie("EngW7tLk6R8","Test 1", "This is test1 item!","Movie", "")
    val movieTwo = Movie("abcde","Test 2", "This is test2 item!","Movie", "")
    val movieThree = Movie("abcdfg","Test 23", "This is test3 item!","Movie", "")
    val movieFour = Movie("abdf","Test 4", "This is test4 item!","Movie", "")
    val movieFive = Movie("adsa","Test 5", "This is test5 item!","Movie", "")


    val playlistOne = Playlist("pl1","Playlist 1", arrayListOf(movieOne, movieTwo))
    val playlistTwo = Playlist("pl2", "Playlist 2", arrayListOf(movieThree, movieFour, movieFive))

    val playlistFavorite = Playlist("favorite", "Favorites", arrayListOf())

    val playlistSampleList = arrayListOf(playlistOne, playlistTwo)
}