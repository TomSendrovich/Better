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
) : Parcelable {
    companion object {
        fun buildScoreText(fixture: Fixture): String {
            val homeScore = fixture.score.fullTime.home.toString()
            val awayScore = fixture.score.fullTime.away.toString()
            return "${homeScore}-${awayScore}"
        }

        fun buildStatusText(fixture: Fixture): String {
            val status = fixture.status.short
            if (status == "NS") {
                var time =
                    fixture.date.substringAfter('T').substringBefore('+').substringBeforeLast(':')
                val hour = time.substring(0, 2).toInt() + 2
                time = hour.toString() + time.substring(2, 5)
                return time

            }
            return status
        }
    }
}


