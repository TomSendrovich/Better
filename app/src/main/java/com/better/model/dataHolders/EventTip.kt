package com.better.model.dataHolders

/**
 * Represents an event tip.
 * @property tipID id of the tip
 * @property userID id of the user
 * @property fixtureID id of the fixture
 * @property description description of the tip (free style text)
 * @property tipValue The tip value
 *
 *  Available tip values:
 *  0 - draw
 *  1 - home wins
 *  2 - away wins
 */
data class EventTip(
    val tipID: String,
    val userID: String,
    val fixtureID: String,
    val description: String,
    val tipValue: Long?
)
