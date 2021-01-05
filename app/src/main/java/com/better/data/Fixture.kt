package com.better.data

import java.util.*

data class Fixture(
    val id: Int,
    val date: Date,
    val timestamp: Int,
    val timezone: String,
    val status: Status,
    val goals: Goals,
    val league: Int,
    val score: Score,
    val home: FixtureTeam,
    val away: FixtureTeam
)
