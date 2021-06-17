package com.better.model

import com.better.model.dataHolders.EventTip

object StatsCalculator {
    private const val TAG = "StatsCalculator"

    fun calculateHits(tips: List<EventTip>): HashMap<Long, IntArray> {
        val countMap = hashMapOf<Long, IntArray>()

        tips.forEach { tip ->
            if (tip.isHit != null) {
                countMap.putIfAbsent(tip.league, IntArray(2))
                if (tip.isHit == true) {
                    countMap[tip.league]!!.hit++
                } else {
                    countMap[tip.league]!!.miss++
                }
            }
        }
        return countMap
    }

    fun calculateGuesses(tips: List<EventTip>): HashMap<Long, Int> {
        val countMap = hashMapOf<Long, Int>()
        countMap.putIfAbsent(0, 0)
        countMap.putIfAbsent(1, 0)
        countMap.putIfAbsent(2, 0)

        tips.forEach { tip ->
            countMap[tip.tipValue] = (countMap[tip.tipValue])!! + 1
        }

        return countMap
    }

    fun calculatePercentByTeam(tips: List<EventTip>, teamName: String): Double {
        var hit = 0.0
        var miss = 0.0
        tips.forEach { tip ->
            if ((tip.homeName == teamName || tip.awayName == teamName) && tip.isHit != null) {
                if (tip.isHit == true) {
                    hit++
                } else {
                    miss++
                }
            }
        }
        return hit / (hit + miss)
    }

    fun calculateBestTeamGuess(tips: List<EventTip>): HashMap<String, Double> {
        val countMap = hashMapOf<String, IntArray>()

        tips.forEach { tip ->
            if (tip.isHit != null) {
                countMap.putIfAbsent(tip.homeName, IntArray(2))
                countMap.putIfAbsent(tip.awayName, IntArray(2))
                if (tip.isHit == true) {
                    countMap[tip.homeName]!!.hit++
                    countMap[tip.awayName]!!.hit++
                } else {
                    countMap[tip.homeName]!!.miss++
                    countMap[tip.awayName]!!.miss++
                }
            }
        }

        val results = hashMapOf<String, Double>()
        countMap.keys.forEach { team ->
            val hit = countMap[team]!!.hit.toDouble()
            val miss = countMap[team]!!.miss.toDouble()
            val percent = hit / (hit + miss)

            results[team] = percent * 100
        }

        return results
    }

    fun calculatePercentByLeague(tips: List<EventTip>, leagueID: Long): Double {
        var hit = 0.0
        var miss = 0.0
        tips.forEach { tip ->
            if (tip.league == leagueID && tip.isHit != null) {
                if (tip.isHit == true) {
                    hit++
                } else {
                    miss++
                }
            }
        }
        return hit / (hit + miss)
    }

    private var IntArray.hit
        set(value) = set(0, value)
        get() = get(0)
    private var IntArray.miss
        set(value) = set(1, value)
        get() = get(1)
}
