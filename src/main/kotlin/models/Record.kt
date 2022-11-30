package models

data class Record(val recordName: String,
                val recordCost: Int,
                val recordGenre: String,
                val isRecordOwned :Boolean
                ){
}