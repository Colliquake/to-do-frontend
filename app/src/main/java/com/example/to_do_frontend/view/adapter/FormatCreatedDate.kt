package com.example.to_do_frontend.view.adapter

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

fun FormatCreatedDate(instant: Instant): String {
    val zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
    val month = zdt.month.toString().lowercase().replaceFirstChar(Char::uppercaseChar)
    val day = zdt.dayOfMonth.toString()
    val year = zdt.year.toString()
    
    val createdString = "Created: ${month} ${day} ${year}"
    
    return createdString
}