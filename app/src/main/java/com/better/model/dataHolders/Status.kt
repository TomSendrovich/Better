package com.better.model.dataHolders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents a fixture status.
 * @property short short string represent status (NS, FT, TBD...)
 * @property long long string represent status (Not Started, Full Time...)
 * @property elapsed time (in minutes) past since fixture started
 *
 * Available fixtures status:
 *
 * TBD : Time To Be Defined
 * NS : Not Started
 * 1H : First Half, Kick Off
 * HT : Halftime
 * 2H : Second Half, 2nd Half Started
 * ET : Extra Time
 * P : Penalty In Progress
 * FT : Match Finished
 * AET : Match Finished After Extra Time
 * PEN : Match Finished After Penalty
 * BT : Break Time (in Extra Time)
 * SUSP : Match Suspended
 * INT : Match Interrupted
 * PST : Match Postponed
 * CANC : Match Cancelled
 * ABD : Match Abandoned
 * AWD : Technical Loss
 * WO : WalkOver
 */
@Parcelize
data class Status(
    val short: String,
    val long: String,
    val elapsed: Long?,
) : Parcelable
