import mu.KotlinLogging
import utils.ScannerInput

import java.lang.System.exit


private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        Record Shop App         |
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
    logger.info { "addRecord() function invoked" }
}

fun listRecords(){
    logger.info { "listRecords() function invoked" }
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