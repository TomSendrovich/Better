package com.better.model.dataHolders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents a fixture score.
 * @property halfTime score situation after half time
 * @property fullTime score situation after full time
 * @property extraTime score situation after extra time
 * @property penalty score situation after penalty
 */
@Parcelize
data class Score(
    val halfTime: Goals,
    val fullTime: Goals,
    val extraTime: Goals,
    val penalty: Goals
) : Parcelable
