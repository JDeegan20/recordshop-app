import controllers.RecordAPI
import models.Record
import models.Song
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

import java.lang.System.exit


private val logger = KotlinLogging.logger {}

//private val recordAPI = RecordAPI(XMLSerializer(File("records.xml")))
private val recordAPI = RecordAPI(JSONSerializer(File("records.json")))
fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > -----------------------------------------------------
         > |                Welcome to Josh's                  |
         > |                    Record Shop                    |
         > -----------------------------------------------------
         > | Record Menu                                       |
         > |   1) Add a record                                 |
         > |   2) List all records                             |
         > |   3) Update a record                              |
         > |   4) Delete a record                              |
         > |   5) Own a record                                 |
         > |   6) Search a record                              |
         > |   7) Search by Genre                              |
         > -----------------------------------------------------  
         > | Song Menu                                         | 
         > |   8) Add song to a record                         |
         > |   9) Update song contents on a record             |
         > |  10) Delete song from a record                    |
         > |  11) Search for a song                            |
         > -----------------------------------------------------  
         > |  20) Save                                         |   
         > |  21) Load                                         |
         > |   0) Exit app                                     |
         > -----------------------------------------------------
         > ==>> """.trimMargin(">"))

}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addRecord()
            2  -> listRecords()
            3  -> updateRecord()
            4  -> deleteRecord()
            5  -> ownRecord ()
            6  -> searchRecords()
            7  -> searchRecordsByGenre()
            8  -> addSongToRecord()
            9 -> updateSongNameInRecord ()
            10 -> deleteASong ()
            11 -> searchSongs ()
            20 -> save()
            21 -> load()
            0  -> exitApp()
            else -> println("Invalid option ${option}")
        }
    } while (true)
}

fun addRecord(){

    val recordName = readNextLine("Enter a name for the record: ")
    val recordCost = readNextInt("Enter a cost: ")
    val recordGenre = readNextLine("Enter a genre for the record: ")
    val isAdded = recordAPI.add(Record(recordName, recordCost, recordGenre, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listRecords(){
    if (recordAPI.numberOfRecords() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL records          |
                  > |   2) View ACTIVE records       |
                  > |   3) View OWNED records        |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllRecords();
            2 -> listActiveRecords();
            3 -> listOwnedRecords();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No records stored");
    }
}

fun listAllRecords() {
    println(recordAPI.listAllRecords())
}

fun listActiveRecords() {
    println(recordAPI.listActiveRecords())
}

fun listOwnedRecords() {
    println(recordAPI.listOwnedRecords())
}


fun updateRecord(){
    listRecords()
    if (recordAPI.numberOfRecords() > 0) {

        val indexToUpdate = readNextInt("Enter the index of the record to update: ")
        if (recordAPI.isValidIndex(indexToUpdate)) {
            val recordName = readNextLine("Enter a name for the record: ")
            val recordCost = readNextInt("Enter the cost : ")
            val recordGenre = readNextLine("Enter the genre of the record: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (recordAPI.updateRecord(indexToUpdate, Record(recordName, recordCost, recordGenre, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no records for this index number")
        }
    }
}

fun ownRecord() {
    listActiveRecords()
    if (recordAPI.numberOfActiveRecords() > 0) {

        val recordToOwn = readNextInt("Enter the index of the record: ")

        if (recordAPI.ownRecord(recordToOwn)) {
            println("Successful!")
        } else {
            println("NOT Successful")
        }
    }
}

fun searchRecords() {
    val searchName = readNextLine("Enter the name to search by: ")
    val searchResults = recordAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No records found")
    } else {
        println(searchResults)
    }
}

fun searchRecordsByGenre() {
    val searchGenre = readNextLine("Enter the genre to search by: ")
    val searchResults = recordAPI.searchByGenre(searchGenre)
    if (searchResults.isEmpty()) {
        println("No records found")
    } else {
        println(searchResults)
    }
}


fun deleteRecord(){
    listRecords()
    if (recordAPI.numberOfRecords() > 0) {

        val indexToDelete = readNextInt("Enter the index of the record to delete: ")

        val recordToDelete = recordAPI.deleteRecord(indexToDelete)
        if (recordToDelete != null) {
            println("Delete Successful! Deleted record: ${recordToDelete.recordName}")
        } else {
            println("Delete has not been successful")
        }
    }
}



// Items

private fun addSongToRecord() {
    val record: Record? = askUserToChooseActiveRecord()
    if (record != null) {
        if (record.addSong(Song(songName = readNextLine("\t Item Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}




fun updateSongNameInRecord() {
    val record: Record? = askUserToChooseActiveRecord()
    if (record != null) {
        val song: Song? = askUserToChooseSong(record)
        if (song != null) {
            val newName = readNextLine("Enter new name: ")
            if (record.update(song.songId, Song(songName = newName))) {
                println("Song name updated")
            } else {
                println("Song name NOT updated")
            }
        } else {
            println("Invalid song Id")
        }
    }
}

fun deleteASong() {
    val record: Record? = askUserToChooseActiveRecord()
    if (record != null) {
        val song: Song? = askUserToChooseSong(record)
        if (song != null) {
            val isDeleted = record.delete(song.songId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}


fun searchSongs() {
    val searchName = readNextLine("Enter the song name to search by: ")
    val searchResults = recordAPI.searchSongByName(searchName)
    if (searchResults.isEmpty()) {
        println("No songs found")
    } else {
        println(searchResults)
    }
}
fun exitApp(){
    logger.info { "exitApp() function invoked" }
    exit(0)
}

fun save() {
    try {
        recordAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        recordAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

private fun askUserToChooseActiveRecord(): Record? {
    listActiveRecords()
    if (recordAPI.numberOfActiveRecords() > 0) {
        val record = recordAPI.findRecord(readNextInt("\nEnter the id of the record: "))
        if (record != null) {
            if (record.isRecordOwned) {
                println("Record is NOT Active, it is Owned")
            } else {
                return record
            }
        } else {
            println("Record id is not valid")
        }
    }
    return null
}

private fun askUserToChooseSong(record: Record): Song? {
    if (record.numberOfSongs() > 0) {
        print(record.listSongs())
        return record.findOne(readNextInt("\nEnter the id of the song: "))
    }
    else{
        println ("No songs for chosen record")
        return null
    }
}


