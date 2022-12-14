package controllers

import models.Record
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class RecordAPITest {

    private var daftPunk: Record? = null
    private var pinkFloyd: Record? = null
    private var fleetwoodMac: Record? = null
    private var michaelJackson: Record? = null
    private var dizzeeRascal: Record? = null
    private var populatedRecords: RecordAPI? =
        RecordAPI(XMLSerializer(File("records.xml")))
    private var emptyRecords: RecordAPI? =
        RecordAPI(XMLSerializer(File("records.xml")))

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
        fun `listOwnedRecords returns no owned records when ArrayList is empty`() {
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
        fun `listRecordsBySelectedCost returns No Records when ArrayList is empty`() {
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
        @Test
        fun `listRecordsBySelectedGenre returns No Records when ArrayList is empty`() {
            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.listRecordsBySelectedGenre("Pop").lowercase().contains("no records")
            )
        }
        @Test
        fun `listRecordsBySelectedGenre returns no records when no records of that genre exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            val genre2String = populatedRecords!!.listRecordsBySelectedGenre("Punk").lowercase()
            assertTrue(genre2String.contains("no records"))
            assertTrue(genre2String.contains("2"))
        }

        @Test
        fun `listRecordsBySelectedGenre returns all records that match that genre when records of that genre exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            val genre1String = populatedRecords!!.listRecordsBySelectedGenre("Punk").lowercase()
            assertTrue(genre1String.contains("Rock"))
            assertTrue(genre1String.contains("Punk"))
            assertTrue(genre1String.contains("Rap"))
            assertFalse(genre1String.contains("Electronic"))
            assertFalse(genre1String.contains("Grime"))
            assertFalse(genre1String.contains("Trap"))
            assertFalse(genre1String.contains("Metal"))


            val genre4String = populatedRecords!!.listRecordsBySelectedGenre("Pop").lowercase(Locale.getDefault())
            assertTrue(genre4String.contains("Rock"))
            assertTrue(genre4String.contains("Punk"))
            assertFalse(genre4String.contains("Rap"))
            assertTrue(genre4String.contains("Electronic"))
            assertTrue(genre4String.contains("Grime"))
            assertFalse(genre4String.contains("Trap"))
            assertFalse(genre4String.contains("Metal"))
        }
    }



    @Nested
    inner class DeleteRecords {
        @Test
        fun `deleting a record that does not exist, returns null`() {
            assertNull(emptyRecords!!.deleteRecord(0))
            assertNull(populatedRecords!!.deleteRecord(1))
            assertNull(populatedRecords!!.deleteRecord(5))
        }

        @Test
        fun `deleting a record that exists delete and returns deleted object`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            assertEquals(dizzeeRascal, populatedRecords!!.deleteRecord(4))
            assertEquals(4, populatedRecords!!.numberOfRecords())
            assertEquals(daftPunk, populatedRecords!!.deleteRecord(0))
            assertEquals(3, populatedRecords!!.numberOfRecords())
        }
    }
    @Nested
    inner class UpdateRecords {
        @Test
        fun `updating a record that does not exist returns false`(){
            assertFalse(populatedRecords!!.updateRecord(6, Record("Back in Black", 50, "Metal", false)))
            assertFalse(populatedRecords!!.updateRecord(1, Record("Graduation", 30, "Hip-Hop", false)))
            assertFalse(emptyRecords!!.updateRecord(0, Record("Loud", 40, "Pop", false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {

            assertEquals(dizzeeRascal, populatedRecords!!.findRecord(4))
            assertEquals("Dizzee Rascal", populatedRecords!!.findRecord(4)!!.recordName)
            assertEquals(3, populatedRecords!!.findRecord(4)!!.recordCost)
            assertEquals("Grime", populatedRecords!!.findRecord(4)!!.recordGenre)


            assertTrue(populatedRecords!!.updateRecord(4, Record("Boy in Da Corner", 30, "Grime", true)))
            assertEquals("Boy in Da Corner", populatedRecords!!.findRecord(4)!!.recordName)
            assertEquals(30, populatedRecords!!.findRecord(4)!!.recordCost)
            assertEquals("Electronic", populatedRecords!!.findRecord(4)!!.recordGenre)
        }

        @Nested
        inner class OwnRecords {
            @Test
            fun `saying you own a record that does not exist returns false`(){
                assertFalse(populatedRecords!!.ownRecord(6))
                assertFalse(populatedRecords!!.ownRecord(-1))
                assertFalse(emptyRecords!!.ownRecord(0))
            }

            @Test
            fun `saying you own a record that is already owned returns false`(){
                assertTrue(populatedRecords!!.findRecord(2)!!.isRecordOwned)
                assertFalse(populatedRecords!!.ownRecord(2))
            }

            @Test
            fun `owning a record that exists returns true and records that the record is owned`() {
                assertFalse(populatedRecords!!.findRecord(1)!!.isRecordOwned)
                assertTrue(populatedRecords!!.ownRecord(1))
                assertTrue(populatedRecords!!.findRecord(1)!!.isRecordOwned)
            }
        }
    }


    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingRecords = RecordAPI(XMLSerializer(File("records.xml")))
            storingRecords.store()

            val loadedRecords = RecordAPI(XMLSerializer(File("records.xml")))
            loadedRecords.load()

            assertEquals(0, storingRecords.numberOfRecords())
            assertEquals(0, loadedRecords.numberOfRecords())
            assertEquals(storingRecords.numberOfRecords(), loadedRecords.numberOfRecords())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {

            val storingRecords = RecordAPI(XMLSerializer(File("records.xml")))
            storingRecords.add(michaelJackson!!)
            storingRecords.add(dizzeeRascal!!)
            storingRecords.add(pinkFloyd!!)
            storingRecords.store()


            val loadedRecords = RecordAPI(XMLSerializer(File("records.xml")))
            loadedRecords.load()


            assertEquals(3, storingRecords.numberOfRecords())
            assertEquals(3, storingRecords.numberOfRecords())
            assertEquals(storingRecords.numberOfRecords(), loadedRecords.numberOfRecords())
            assertEquals(storingRecords.findRecord(0), loadedRecords.findRecord(0))
            assertEquals(storingRecords.findRecord(1), loadedRecords.findRecord(1))
            assertEquals(storingRecords.findRecord(2), loadedRecords.findRecord(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {

            val storingRecords = RecordAPI(JSONSerializer(File("records.json")))
            storingRecords.store()


            val loadedRecords = RecordAPI(JSONSerializer(File("records.json")))
            loadedRecords.load()


            assertEquals(0, storingRecords.numberOfRecords())
            assertEquals(0, loadedRecords.numberOfRecords())
            assertEquals(storingRecords.numberOfRecords(), loadedRecords.numberOfRecords())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {

            val storingRecords = RecordAPI(JSONSerializer(File("records.json")))
            storingRecords.add(michaelJackson!!)
            storingRecords.add(pinkFloyd!!)
            storingRecords.add(dizzeeRascal!!)
            storingRecords.store()


            val loadedRecords = RecordAPI(JSONSerializer(File("records.json")))
            loadedRecords.load()


            assertEquals(3, storingRecords.numberOfRecords())
            assertEquals(3, loadedRecords.numberOfRecords())
            assertEquals(storingRecords.numberOfRecords(), loadedRecords.numberOfRecords())
            assertEquals(storingRecords.findRecord(0), loadedRecords.findRecord(0))
            assertEquals(storingRecords.findRecord(1), loadedRecords.findRecord(1))
            assertEquals(storingRecords.findRecord(2), loadedRecords.findRecord(2))
        }
    }


    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfRecordsCalculatedCorrectly() {
            assertEquals(5, populatedRecords!!.numberOfRecords())
            assertEquals(0, emptyRecords!!.numberOfRecords())
        }

        @Test
        fun numberOfOwnedRecordsCalculatedCorrectly() {
            assertEquals(2, populatedRecords!!.numberOfOwnedRecords())
            assertEquals(0, emptyRecords!!.numberOfOwnedRecords())
        }

        @Test
        fun numberOfActiveRecordsCalculatedCorrectly() {
            assertEquals(3, populatedRecords!!.numberOfActiveRecords())
            assertEquals(0, emptyRecords!!.numberOfActiveRecords())
        }

        @Test
        fun numberOfRecordsByCostCalculatedCorrectly() {
            assertEquals(1, populatedRecords!!.numberOfRecordsByCost(1))
            assertEquals(0, populatedRecords!!.numberOfRecordsByCost(2))
            assertEquals(1, populatedRecords!!.numberOfRecordsByCost(3))
            assertEquals(2, populatedRecords!!.numberOfRecordsByCost(4))
            assertEquals(1, populatedRecords!!.numberOfRecordsByCost(5))
            assertEquals(0, populatedRecords!!.numberOfRecordsByCost(1))
        }
        @Test
        fun numberOfRecordsByGenreCalculatedCorrectly() {
            assertEquals(1, populatedRecords!!.numberOfRecordsByGenre("Funk"))
            assertEquals(5, populatedRecords!!.numberOfRecordsByGenre("Hip-Hop"))
            assertEquals(3, populatedRecords!!.numberOfRecordsByGenre("Soft Rock"))
            assertEquals(2, populatedRecords!!.numberOfRecordsByGenre("Death Metal"))
            assertEquals(1, populatedRecords!!.numberOfRecordsByGenre("Electronic"))
            assertEquals(8, emptyRecords!!.numberOfRecordsByGenre("Opera"))
        }


    }


    @Nested
    inner class SearchMethods {

        @Test
        fun `search records by name returns no records when no records with that name exist`() {

            assertEquals(5, populatedRecords!!.numberOfRecords())
            val searchResults = populatedRecords!!.searchByName("no results expected")
            assertTrue(searchResults.isEmpty())


            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.searchByName("").isEmpty())
        }

        @Test
        fun `search records by name returns records when records with that name exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())

            var searchResults = populatedRecords!!.searchByName("Rumours")
            assertTrue(searchResults.contains("Rumours"))
            assertFalse(searchResults.contains("Thriller"))

            searchResults = populatedRecords!!.searchByName("Album")
            assertTrue(searchResults.contains("Rumours"))
            assertTrue(searchResults.contains("Thriller"))
            assertFalse(searchResults.contains("Boy in da Corner"))


            searchResults = populatedRecords!!.searchByName("alBuM")
            assertTrue(searchResults.contains("Rumours"))
            assertTrue(searchResults.contains("Thriller"))
            assertFalse(searchResults.contains("Boy in da Corner"))
        }

        @Test
        fun `search records by genre returns no records when no records with that genre exist`() {

            assertEquals(5, populatedRecords!!.numberOfRecords())
            val searchResults = populatedRecords!!.searchByGenre("no results expected")
            assertTrue(searchResults.isEmpty())


            assertEquals(0, emptyRecords!!.numberOfRecords())
            assertTrue(emptyRecords!!.searchByGenre("").isEmpty())
        }

        @Test
        fun `search records by genre returns records when records with that genre exist`() {
            assertEquals(5, populatedRecords!!.numberOfRecords())

            var searchResults = populatedRecords!!.searchByGenre("Rumours")
            assertTrue(searchResults.contains("Rumours"))
            assertFalse(searchResults.contains("Thriller"))

            searchResults = populatedRecords!!.searchByGenre("Thriller")
            assertTrue(searchResults.contains("Thriller"))
            assertFalse(searchResults.contains("Discovery"))
            assertFalse(searchResults.contains("Boy in da Corner"))


            searchResults = populatedRecords!!.searchByGenre("th")
            assertFalse(searchResults.contains("Rumours"))
            assertTrue(searchResults.contains("Thriller"))
            assertFalse(searchResults.contains("Boy in da Corner"))
        }



    }

}


