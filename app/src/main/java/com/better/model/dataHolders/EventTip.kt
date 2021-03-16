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
@Parcelize
data class EventTip(
    val tipID: String,
    val userID: String,
    val fixtureID: String,
    val description: String?,
    val tipValue: Long
): Parcelable {
    companion object {
        fun getTipValue(eventTip: EventTip):String {
            return eventTip.tipValue.toString()
        }

        fun getTipDescription(eventTip: EventTip): String? {
            return eventTip.description
        }

    }
}


