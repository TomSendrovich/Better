package com.better.model.dataHolders

import android.os.Parcelable
import com.better.utils.DateUtils
import kotlinx.android.parcel.Parcelize
import java.util.*

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
    val away: FixtureTeam,
    var prediction: Long,
) : Parcelable {
    companion object {
        fun buildScoreText(fixture: Fixture): String {
            val homeScore = fixture.goals.home.toString()
            val awayScore = fixture.goals.away.toString()
            return "${homeScore}-${awayScore}"
        }

        fun buildTimeText(fixture: Fixture): String {
            return when (fixture.status.short) {
                "HT" -> "Halftime"
                "P" -> "Penalty"
                else -> "${fixture.status.elapsed}'"
            }
        }

        fun buildHead2HeadText(fixture: Fixture): String {
            return "${fixture.home.name} - ${fixture.away.name}"
        }
    }

    fun getCalendar(): Calendar {
        return DateUtils.getCalendarFromTimestamp(timestamp)
    }
}


