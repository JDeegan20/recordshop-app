import controllers.RecordAPI
import models.Record
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

import java.lang.System.exit


private val logger = KotlinLogging.logger {}

private val recordAPI = RecordAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |       Welcome to Josh's        |
         > |          Record Shop           |
         > ----------------------------------
         > | Record Menu                    |
         > |   1) Add a record              |
         > |   2) List all records          |
         > |   3) Update a record           |
         > |   4) Delete a record           |
         > ----------------------------------
         > |   0) Exit app                  |
         > ----------------------------------
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
    println(recordAPI.listAllRecords())
}

fun updateRecord(){
    logger.info { "updateRecord() function invoked" }
}

fun deleteRecord(){
    logger.info { "deleteRecord() function invoked" }
}

fun exitApp(){
    logger.info { "exitApp() function invoked" }
    exit(0)
}