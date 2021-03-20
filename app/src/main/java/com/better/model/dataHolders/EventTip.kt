package com.better.model.dataHolders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    var tipID: String,
    var userID: String,
    var fixtureID: Long,
    var description: String?,
    var tipValue: Long,
    var isHit: Boolean?
)


