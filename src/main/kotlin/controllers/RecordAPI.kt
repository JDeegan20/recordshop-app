package controllers

import models.Record
import persistence.Serializer

class RecordAPI (serializerType: Serializer){
    private var serializer: Serializer = serializerType

    private var records = ArrayList<Record>()

    fun add(record: Record): Boolean {
        return records.add(record)
    }

    fun deleteRecord(indexToDelete: Int): Record? {
        return if (isValidListIndex(indexToDelete, records)) {
            records.removeAt(indexToDelete)
        } else null
    }

    fun updateRecord(indexToUpdate: Int, record: Record?): Boolean {
        val foundRecord = findRecord(indexToUpdate)

        if ((foundRecord != null) && (record != null)) {
            foundRecord.recordName = record.recordName
            foundRecord.recordCost = record.recordCost
            foundRecord.recordGenre = record.recordGenre
            return true
        }

        return false
    }

    fun listAllRecords(): String {
        return if (records.isEmpty()) {
            "No records stored"
        } else {
            var listOfRecords = ""
            for (i in records.indices) {
                listOfRecords += "${i}: ${records[i]} \n"
            }
            listOfRecords
        }
    }

    fun listActiveRecords(): String {
        return if (numberOfActiveRecords() == 0) {
            "No active records are stored"
        } else {
            var listOfActiveRecords = ""
            for (record in records) {
                if (!record.isRecordOwned) {
                    listOfActiveRecords += "${records.indexOf(record)}: $record \n"
                }
            }
            listOfActiveRecords
        }
    }

    fun listOwnedRecords(): String {
        return if (numberOfOwnedRecords() == 0) {
            "No owned records are stored"
        } else {
            var listOfOwnedRecords = ""
            for (record in records) {
                if (record.isRecordOwned) {
                    listOfOwnedRecords += "${records.indexOf(record)}: $record \n"
                }
            }
            listOfOwnedRecords
        }
    }

    fun numberOfOwnedRecords(): Int {
        var counter = 0
        for (record in records) {
            if (record.isRecordOwned) {
                counter++
            }
        }
        return counter

    }

    fun numberOfActiveRecords(): Int {
        var counter = 0
        for (record in records) {
            if (!record.isRecordOwned) {
                counter++
            }
        }
        return counter
    }

    fun numberOfRecords(): Int {
        return records.size
    }


    fun listRecordsBySelectedCost(cost: Int): String {
        return if (records.isEmpty()) {
            "No records "
        } else {
            var listOfRecords = ""
            for (i in records.indices) {
                if (records[i].recordCost == cost) {
                    listOfRecords +=
                        """$i: ${records[i]}
                        """.trimIndent()
                }
            }
            if (listOfRecords.equals("")) {
                "No records with cost: $cost"
            } else {
                "${numberOfRecordsByCost(cost)} records of cost $cost: $listOfRecords"
            }
        }
    }

    fun numberOfRecordsByCost(cost: Int): Int {
        var counter = 0
        for (record in records) {
            if (record.recordCost == cost) {
                counter++
            }
        }
        return counter

    }

    fun findRecord(index: Int): Record? {
        return if (isValidListIndex(index, records)) {
            records[index]
        } else null
    }


    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, records);
    }


    @Throws(Exception::class)
    fun load() {
        records = serializer.read() as ArrayList<Record>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(records)
    }

}