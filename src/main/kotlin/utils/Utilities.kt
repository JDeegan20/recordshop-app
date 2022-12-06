package utils

import models.Song
import models.Record

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(recordsToFormat: List<Record>): String =
        recordsToFormat
            .joinToString(separator = "\n") { record ->  "$record" }

    @JvmStatic
    fun formatSetString(songsToFormat: Set<Song>): String =
        songsToFormat
            .joinToString(separator = "\n") { song ->  "\t$song" }

}
