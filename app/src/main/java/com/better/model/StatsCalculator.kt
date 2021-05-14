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

    private var IntArray.hit
        set(value) = set(0, value)
        get() = get(0)
    private var IntArray.miss
        set(value) = set(1, value)
        get() = get(1)
}
