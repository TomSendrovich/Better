package com.better.model.dataHolders

/**
 * Represents a venue (stadium).
 */
data class Venue(
    val id: Long,
    val name: String,
    val city: String,
    val image: String,
    val address: String,
    val capacity: Long,
    val surface: String
)
