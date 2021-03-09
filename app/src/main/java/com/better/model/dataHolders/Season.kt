package com.better.model.dataHolders

// TODO: 09/03/2021 is this data class necessary?
/**
 * Represents a season.
 */
data class Season(
    val year: Long,
    val start: String,
    val end: String,
    val current: Boolean,
    val coverage: String,
)
