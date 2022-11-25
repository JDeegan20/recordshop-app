package controllers

import models.Record
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RecordAPITest {

    private var daftPunk: Record? = null
    private var pinkFloyd: Record? = null
    private var fleetwoodMac: Record? = null
    private var michaelJackson: Record? = null
    private var dizzeeRascal: Record? = null
    private var populatedRecords: RecordAPI? = RecordAPI()
    private var emptyRecords: RecordAPI? = RecordAPI()

    @BeforeEach
    fun setup() {
        daftPunk = Record("Discovery", 50, "Electronic", false)
        pinkFloyd = Record("The Dark Side Of The Moon", 80, "Rock", false)
        fleetwoodMac = Record("Rumours", 40, "SoftRock", false)
        michaelJackson = Record("Thriller", 90, "Pop", false)
        dizzeeRascal = Record("Boy in da Corner", 30, "Grime", false)


        populatedRecords!!.add(daftPunk!!)
        populatedRecords!!.add(pinkFloyd!!)
        populatedRecords!!.add(fleetwoodMac!!)
        populatedRecords!!.add(michaelJackson!!)
        populatedRecords!!.add(dizzeeRascal!!)
    }

    @AfterEach
    fun tearDown() {
        daftPunk = null
        pinkFloyd = null
        fleetwoodMac = null
        michaelJackson = null
        dizzeeRascal = null
        populatedRecords = null
        emptyRecords = null
    }

    @Nested
    inner class AddRecords {


        @Test
        fun `adding a Record to a populated list adds to ArrayList`() {
            val newRecord = Record("Marshall Mathers EP", 70, "Rap", false)
            assertEquals(5, populatedRecords!!.numberOfRecords())
            assertTrue(populatedRecords!!.add(newRecord))
            assertEquals(6, populatedRecords!!.numberOfRecords())
            assertEquals(newRecord, populatedRecords!!.findRecord(populatedRecords!!.numberOfRecords() - 1))
        }

        @Test
        fun `adding a Record to an empty list adds to ArrayList`() {
            val newRecord = Record("Marshall Mathers EP", 70, "Rap", false)
            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.add(newRecord))
            assertEquals(1, emptyRecords!!.numberOfRecords())
            assertEquals(newRecord, emptyRecords!!.findRecord(emptyRecords!!.numberOfRecords() - 1))
        }
    }


    @Nested
    inner class ListRecords {


        @Test
        fun `listAllRecords returns No Records Stored message when ArrayList is empty`() {
            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.listAllRecords().lowercase().contains("no records"))
        }

        @Test
        fun `listAllRecords returns Records when ArrayList has records stored`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            val recordsString = populatedRecords!!.listAllRecords().lowercase()
            assertTrue(recordsString.contains("Daft Punk"))
            assertTrue(recordsString.contains("Pink Floyd"))
            assertTrue(recordsString.contains("Fleetwood Mac"))
            assertTrue(recordsString.contains("Michael Jackson"))
            assertTrue(recordsString.contains("Dizzee Rascal"))
        }


        @Test
        fun `listActiveRecords returns no active records stored when ArrayList is empty`() {
            assertEquals(0, emptyRecords!!.numberOfActiveRecords())
            assertTrue(
                emptyRecords!!.listActiveRecords().lowercase().contains("no active records")
            )
        }

        @Test
        fun `listActiveRecords returns active records when ArrayList has active records stored`() {
            assertEquals(3, populatedRecords!!.numberOfActiveRecords())
            val activeRecordsString = populatedRecords!!.listActiveRecords().lowercase()
            assertTrue(activeRecordsString.contains("Daft Punk"))
            assertFalse(activeRecordsString.contains("Pink Floyd"))
            assertTrue(activeRecordsString.contains("Fleetwood Mac"))
            assertTrue(activeRecordsString.contains("Michael Jackson"))
            assertFalse(activeRecordsString.contains("Dizzee Rascal"))
        }

        @Test
        fun `listOwnedNotes returns no owned notes when ArrayList is empty`() {
            assertEquals(0, emptyRecords!!.numberOfOwnedRecords())
            assertTrue(
                emptyRecords!!.listOwnedRecords().lowercase().contains("no owned records")
            )
        }

        @Test
        fun `listOwnedRecords returns owned records when ArrayList has owned records stored`() {
            assertEquals(2, populatedRecords!!.numberOfOwnedRecords())
            val ownedRecordString = populatedRecords!!.listOwnedRecords().lowercase()
            assertFalse(ownedRecordString.contains("Daft Punk"))
            assertTrue(ownedRecordString.contains("Pink Floyd"))
            assertFalse(ownedRecordString.contains("Fleetwood Mac"))
            assertFalse(ownedRecordString.contains("Michael Jackson"))
            assertTrue(ownedRecordString.contains("Dizzee Rascal"))
        }

        @Test
        fun `listNotesBySelectedCost returns No Records when ArrayList is empty`() {
            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.listRecordsBySelectedCost(1).lowercase().contains("no records")
            )
        }

        @Test
        fun `listRecordsBySelectedCost returns no records when no records of that cost exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            val cost2String = populatedRecords!!.listRecordsBySelectedCost(2).lowercase()
            assertTrue(cost2String.contains("no records"))
            assertTrue(cost2String.contains("2"))
        }

        @Test
        fun `listRecordsBySelectedCost returns all records that match that cost when records of that cost exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            val cost1String = populatedRecords!!.listRecordsBySelectedCost(1).lowercase()
            assertTrue(cost1String.contains("1 Record"))
            assertTrue(cost1String.contains("Cost 20"))
            assertTrue(cost1String.contains("Pink Floyd"))
            assertFalse(cost1String.contains("Dizzee Rascal"))
            assertFalse(cost1String.contains("Daft Punk"))
            assertFalse(cost1String.contains("Fleetwood Mac"))
            assertFalse(cost1String.contains("Michael Jackson"))


            val cost4String = populatedRecords!!.listRecordsBySelectedCost(4).lowercase()
            assertTrue(cost4String.contains("2 Records"))
            assertTrue(cost4String.contains("Cost 40"))
            assertFalse(cost4String.contains("Dizzee Rascal"))
            assertTrue(cost4String.contains("Fleetwood Mac"))
            assertTrue(cost4String.contains("Michael Jackson"))
            assertFalse(cost4String.contains("Daft Punk"))
            assertFalse(cost4String.contains("Pink Floyd"))
        }

    }
}