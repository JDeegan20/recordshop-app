package controllers

import models.Record
class RecordAPI {
    private var records = ArrayList<Record>()

    fun add(record: Record): Boolean {
        return records.add(record)
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
        return ""
    }

    fun listOwnedRecords(): String {
        return ""
    }

    fun numberOfOwnedRecords(): Int {
        return 0
    }

    fun numberOfActiveRecords(): Int {
        return 0
    }

    fun numberOfRecords(): Int {
        return records.size
    }


    fun listRecordsBySelectedCost(cost: Int): String {
        return ""
    }

    fun numberOfRecordsByCost(): Int {
        return 0
    }

    fun findRecord(index: Int): Record? {
        return if (isValidListIndex(index, records)) {
            records[index]
        } else null
    }


    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}