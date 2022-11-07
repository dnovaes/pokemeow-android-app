package com.dnovaes.commons.utilities.extensions

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class StringExtensionsTest {

    private val expectedZoneDateTimeSample: ZonedDateTime
       = ZonedDateTime.of(2022, 10, 30, 0, 0, 0, 0, ZoneId.systemDefault())

    private val givenPatterns = listOf(
        "yyyy-MM-dd",
        "yyyy MM dd",
        "yyyy dd MM",
        "dd MM yyyy",
        "dd/MM/yyyy",
        "MM/dd/yyyy",
        "d/MM/yyyy",
        "MMMM dd yyyy"
    )

    private val givenStringDates = listOf(
        "2022-10-30",
        "2022 10 30",
        "2022 30 10",
        "30 10 2022",
        "30/10/2022",
        "10/30/2022",
        "30/10/2022",
        "OCTOBER 30 2022"
    )

    @Test
    fun `assert conversion from date string to zonedDateTime with CANADIAN locale`() {
        // Given
        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toZonedDateTimeFromCustomDate(givenPatterns[i], Locale.CANADA)
        }

        // Then
        actualResults.forEach { result ->
            assertEquals(expectedZoneDateTimeSample, result)
            assertEquals(
                expectedZoneDateTimeSample.format(DateTimeFormatter.BASIC_ISO_DATE),
                result.format(DateTimeFormatter.BASIC_ISO_DATE)
            )
            assertEquals("OCTOBER", result.month.toString())
            assertEquals(10, result.monthValue)
        }
    }

    @Test
    fun `assert conversion from date string to zonedDateTime with JAPAN locale`() {
        // Given
        val regex = "[^0-9.\\/\\-\\s]".toRegex()
        //JAPAN localte doesnt have MMMM
        val givenStringDates = givenStringDates.filter {
            !regex.containsMatchIn(it)
        }

        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toZonedDateTimeFromCustomDate(givenPatterns[i], Locale.JAPAN)
        }

        // Then
        actualResults.forEach { result ->
            assertEquals(expectedZoneDateTimeSample, result)
            assertEquals(
                expectedZoneDateTimeSample.format(DateTimeFormatter.BASIC_ISO_DATE),
                result.format(DateTimeFormatter.BASIC_ISO_DATE)
            )
            assertEquals("OCTOBER", result.month.toString())
            assertEquals(10, result.monthValue)
        }
    }

    @Test
    fun `conversion between date string and zonedDateTime generates diff time`() {
        // Given
        val sampleLocalDate = LocalDate.of(2022, 10, 30)
        val sampleLocalTime = LocalTime.of(10, 40)
        val expectedResult = ZonedDateTime.of(sampleLocalDate, sampleLocalTime, ZoneId.systemDefault())

        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toZonedDateTimeFromCustomDate(givenPatterns[i], Locale.ENGLISH)
        }

        // Then
        actualResults.forEach { result ->
            println(result)
            assertNotEquals(expectedResult, result)
            assertEquals(
                expectedResult.format(DateTimeFormatter.BASIC_ISO_DATE),
                result.format(DateTimeFormatter.BASIC_ISO_DATE)
            )
            assertEquals(10, result.monthValue)
            assertEquals("OCTOBER", result.month.toString())
            val hourStartOfDay = 0
            assertEquals(hourStartOfDay, result.hour)
            val minuteStartOfDay = 0
            assertEquals(minuteStartOfDay, result.minute)
            assertNotEquals(expectedResult.hour, result.hour)
            assertNotEquals(expectedResult.minute, result.minute)
        }
    }

    @Test
    fun `assert conversion from date string to zonedDateTime with DEFAULT locale`() {
        // Given
        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toZonedDateTimeFromCustomDate(givenPatterns[i], Locale.CANADA)
        }

        // Then
        actualResults.forEach { result ->
            assertEquals(expectedZoneDateTimeSample, result)
            assertEquals(
                expectedZoneDateTimeSample.format(DateTimeFormatter.BASIC_ISO_DATE),
                result.format(DateTimeFormatter.BASIC_ISO_DATE)
            )
            assertEquals("OCTOBER", result.month.toString())
            assertEquals(10, result.monthValue)
        }
    }

    @Test
    fun `assert date string to localDate with CANADA locale`() {
        // Given
        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toLocalDate(givenPatterns[i], Locale.CANADA)
        }

        // Then
        actualResults.forEach { result ->
            assertEquals(expectedZoneDateTimeSample.toLocalDate(), result)
            assertEquals("OCTOBER", result.month.toString())
            assertEquals(10, result.monthValue)
        }
    }

    @Test
    fun `assert date string to localDate with DEFAULT locale`() {
        // Given
        // When
        val actualResults = givenStringDates.mapIndexed { i, dateString ->
            dateString.toLocalDate(givenPatterns[i])
        }

        // Then
        actualResults.forEach { result ->
            assertEquals(expectedZoneDateTimeSample.toLocalDate(), result)
            assertEquals("OCTOBER", result.month.toString())
            assertEquals(10, result.monthValue)
        }
    }

}
