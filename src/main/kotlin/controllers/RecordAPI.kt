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

    fun ownRecord(indexToOwn: Int): Boolean {
        if (isValidIndex(indexToOwn)) {
            val recordToOwn = records[indexToOwn]
            if (!recordToOwn.isRecordOwned) {
               recordToOwn.isRecordOwned = true
                return true
            }
        }
        return false
    }

    fun listAllRecords(): String =
        if  (records.isEmpty()) "No records stored"
        else records.joinToString (separator = "\n") { record ->
            records.indexOf(record).toString() + ": " + record.toString() }

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

    fun listOwnedRecords(): String =
        if (numberOfOwnedRecords() == 0) "No owned records stored"
        else formatListString(records.filter{ record -> record.isRecordOwned })

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
    fun findRecord(index: Int): Record? {
        return if (isValidListIndex(index, records)) {
            records[index]
        } else null
    }

    //refactored counting methods
    fun numberOfOwnedRecords(): Int = records.count{record: Record -> record.isRecordOwned}

    fun numberOfActiveRecords(): Int = records.count{record: Record -> !record.isRecordOwned}

    fun numberOfRecords(): Int = records.size

    fun numberOfRecordsByCost(cost: Int): Int = records.count { p: Record -> p.recordCost == cost }


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



    private fun formatListString(recordsToFormat : List<Record>) : String =
        recordsToFormat
            .joinToString (separator = "\n") { record ->
                records.indexOf(record).toString() + ": " + record.toString() }

}