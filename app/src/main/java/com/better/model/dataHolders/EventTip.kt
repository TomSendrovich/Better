package com.better.model.dataHolders

/**
 * Represents an event tip.
 * @property uid The uid of the user
 * @property event The event itself (fixture)
 * @property tip The tip (0 1 or 2)
 */
data class EventTip(
    val uid: String,
    val event: Fixture,
    val tip: Long
)
