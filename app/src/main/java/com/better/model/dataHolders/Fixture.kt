package com.better.model.dataHolders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Represents a fixture.
 * @property id (e.g 239625)
 * @property date (e.g. "2020-02-06T14:00:00+00:00")
 * @property timestamp (e.g. 1580997600)
 * @property timezone (e.g. "UTC")
 * @property status see for Status data class
 * @property goals see Goals data class
 * @property league league id (e.g. 200)
 * @property score see Score data class
 * @property home home team (see FixtureTeam data class)
 * @property away away team (see FixtureTeam data class)
 */
@Parcelize
data class Fixture(
    val id: Long,
    val date: String,
    val timestamp: Long,
    val timezone: String,
    val status: Status,
    val goals: Goals,
    val league: Long,
    val score: Score,
    val home: FixtureTeam,
    val away: FixtureTeam
) : Parcelable
