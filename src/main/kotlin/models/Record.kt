package models

import utils.Utilities
data class Record(var recordName: String,
                var recordCost: Int,
                var recordGenre: String,
                var isRecordOwned :Boolean,
                var songs : MutableSet<Song> = mutableSetOf())
    {
        private var lastSongId = 0
        private fun getSongId() = lastSongId++

        fun addSong(song: Song) : Boolean {
            song.songId = getSongId()
            return songs.add(song)
        }

        fun numberOfSongs() = songs.size

        fun findOne(id: Int): Song?{
            return songs.find{ song -> song.songId == id }
        }

        fun delete(id: Int): Boolean {
            return songs.removeIf { song -> song.songId == id}
        }

        fun update(id: Int, newSong : Song): Boolean {
            val foundSong = findOne(id)


            if (foundSong != null){
                foundSong.songName = newSong.songName
                foundSong.songRating = newSong.songRating
                return true
            }


            return false
        }

        fun listSongs() =
            if (songs.isEmpty())  "\tno songs added"
            else  utils.Utilities.formatSetString(songs)



        override fun toString(): String {
            val archived = if (isRecordOwned) 'Y' else 'N'
            return "$recordName, Cost($recordCost), Genre($recordGenre) \n${listSongs()}"
        }
    }
