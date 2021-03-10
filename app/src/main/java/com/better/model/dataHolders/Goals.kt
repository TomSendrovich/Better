package com.better.model.dataHolders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents a score situation.
 * @property home goals for home team
 * @property away goals for away team
 */
@Parcelize
data class Goals(
    val home: Long?,
    val away: Long?
) : Parcelable

