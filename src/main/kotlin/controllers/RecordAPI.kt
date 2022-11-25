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

}