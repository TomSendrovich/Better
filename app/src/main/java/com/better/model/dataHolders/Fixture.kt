package com.better.model.dataHolders

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
)
