package com.better.model.dataHolders

/**
 * Represents a league as it stored in a specific fixture.
 * @property id (e.g. 39)
 * @property name (e.g. "Premier League")
 * @property logo league photo url (e.g. "https://media.api-sports.io/football/leagues/39.png")
 * @property country (e.g. "England")
 * @property flag flag photo url (e.g. "https://media.api-sports.io/flags/gb.svg")
 * @property round (e.g "Regular Season - 1")
 * @property season (e.g 2020)
 */
data class FixtureLeague(
    val id: Long,
    val name: String,
    val logo: String,
    val country: String,
    val flag: String,
    val round: String,
    val season: Long,
)
