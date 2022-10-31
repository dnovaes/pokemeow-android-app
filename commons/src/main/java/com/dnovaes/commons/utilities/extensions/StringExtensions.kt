package com.dnovaes.commons.utilities.extensions

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

fun String.toZonedDateTimeFromCustomDate(
    pattern: String,
    locale: Locale = Locale.ENGLISH
): ZonedDateTime {
    val localDate = toLocalDate(pattern, locale)
    return localDate.atStartOfDay(ZoneId.systemDefault())
}

fun String.toLocalDate(pattern: String, locale: Locale = Locale.ENGLISH): LocalDate {
    val formatter = DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ofPattern(pattern))
        .toFormatter(locale)
    return LocalDate.parse(this, formatter)
}
