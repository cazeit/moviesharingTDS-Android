package de.tdsoftware.moviesharing

import de.tdsoftware.moviesharing.data.models.Playlist
import de.tdsoftware.moviesharing.data.models.Movie

/**
 * sample data for user interface to check later network-obtained-data's compatibility with adapters
 */

object Sample {

    private val movieOne = Movie("EngW7tLk6R8","Test 1", "orem ipsum dolor sit amet, consectetur adipiscing elit. In purus sem, aliquam convallis sagittis non, accumsan in lorem. Vestibulum varius est id nisi tempus rhoncus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam pulvinar commodo eros eget mollis. Mauris ac porttitor tortor, et suscipit sem. Sed id ante lectus. Nunc risus risus, semper vulputate pharetra interdum, commodo sollicitudin nulla. Mauris id nisl fringilla, imperdiet elit finibus, interdum nibh. Mauris ornare arcu quis turpis ultricies, eget porta ipsum imperdiet. Ut ut consectetur neque, ut semper mi.\n" +
            "\n" +
            "Morbi euismod aliquam nunc, ac varius ex bibendum iaculis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum a dui sollicitudin, posuere enim in, viverra est. Sed placerat non nisi id commodo. Suspendisse ut cursus magna. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aliquam nec interdum lorem. Praesent in luctus justo, eget luctus magna. Aenean imperdiet molestie metus. Donec in mattis sapien. Fusce id turpis sapien.\n" +
            "\n" +
            "Nunc ut pulvinar lectus. Phasellus ultricies, erat non pellentesque sagittis, mi elit placerat tellus, maximus aliquet magna nulla at est. Morbi eget blandit quam, eget placerat nunc. Sed vehicula erat non porta bibendum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec congue vel diam viverra dapibus. Nam dignissim enim euismod congue posuere. Nulla ut erat id elit dictum porttitor rhoncus quis enim. Integer ipsum nunc, malesuada eget elit vel, semper malesuada felis. Vivamus in quam odio. Aliquam eu sodales odio. Aenean vel sapien rhoncus tellus fringilla iaculis.\n" +
            "\n" +
            "Praesent sed euismod tellus, eget tempor turpis. Etiam mi leo, sagittis ac finibus quis, faucibus eget lorem. Sed pulvinar laoreet felis non porttitor. Donec aliquam id elit mattis accumsan. Fusce semper enim et ipsum mattis, vitae ornare ligula vulputate. Suspendisse at velit aliquet, feugiat sem sagittis, placerat nibh. Morbi in bibendum nisl. Maecenas at erat eget ipsum euismod euismod. Nam faucibus eget nunc quis pharetra. Nulla feugiat congue augue at sodales. Sed ornare, mi in finibus accumsan, tortor nibh ornare tellus, nec venenatis lorem nisl in metus. Mauris vitae accumsan libero. Donec dolor orci, maximus a cursus id, tristique nec mauris.\n" +
            "\n" +
            "Etiam efficitur purus a aliquet tempus. Cras rhoncus, orci eget commodo facilisis, orci lectus accumsan ligula, id placerat turpis neque non tellus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam dapibus iaculis interdum. Fusce molestie arcu in ornare semper. Vestibulum urna magna, efficitur eget volutpat non, scelerisque vitae felis. Curabitur pulvinar elit vitae finibus vestibulum. Ut nec tincidunt nunc, id pellentesque sem. Suspendisse ac nisi arcu. Nam id risus sapien.","Movie", "")
    private val movieTwo = Movie("abcde","Test 2", "This is test2 item!","Movie", "")
    private val movieThree = Movie("abcdfg","Test 23", "This is test3 item!","Movie", "")
    private val movieFour = Movie("abdf","Test 4", "This is test4 item!","Movie", "")
    private val movieFive = Movie("adsa","Test 5", "This is test5 item!","Movie", "")

    private val playlistOne = Playlist("pl1","Playlist 1", arrayListOf(movieOne, movieTwo))
    private val playlistTwo = Playlist("pl2", "Playlist 2", arrayListOf(movieThree, movieFour, movieFive))

    val playlistFavorite = Playlist("favorite", "Favorites", arrayListOf())

    val playlistSampleList = arrayListOf(playlistOne, playlistTwo)
}