package controllers

import models.Record
import org.junit.jupiter.api.AfterEach
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
    }
}