package com.better

const val DB_COLLECTION_FIXTURES: String = "fixtures"
const val DB_COLLECTION_LEAGUES: String = "leagues"
const val DB_COLLECTION_TEAMS: String = "teams"
const val DB_COLLECTION_USERS: String = "users"
const val DB_COLLECTION_VENUES: String = "venues"

const val FIXTURE: String = "fixture"
const val ID: String = "id"
const val DATE: String = "date"
const val TIMESTAMP: String = "timestamp"
const val TIMEZONE: String = "timezone"
const val STATUS: String = "status"
const val ELAPSED: String = "elapsed"
const val LONG: String = "long"
const val SHORT: String = "short"
const val GOALS: String = "goals"
const val HOME: String = "home"
const val AWAY: String = "away"
const val SCORE: String = "score"
const val EXTRA_TIME: String = "extratime"
const val FULL_TIME: String = "fulltime"
const val HALF_TIME: String = "halftime"
const val PENALTY: String = "penalty"
const val LEAGUE: String = "league"
const val TEAMS: String = "teams"
const val LOGO: String = "logo"
const val NAME: String = "name"
const val WINNER: String = "winner"


const val FIXTURE_ID: String = "${FIXTURE}.${ID}"
const val FIXTURE_DATE: String = "${FIXTURE}.${DATE}"
const val FIXTURE_TIMESTAMP: String = "${FIXTURE}.${TIMESTAMP}"
const val FIXTURE_TIMEZONE: String = "${FIXTURE}.${TIMEZONE}"
const val FIXTURE_STATUS_ELAPSED: String = "${FIXTURE}.${STATUS}.${ELAPSED}"
const val FIXTURE_STATUS_LONG: String = "${FIXTURE}.${STATUS}.${LONG}"
const val FIXTURE_STATUS_SHORT: String = "${FIXTURE}.${STATUS}.${SHORT}"
const val GOALS_HOME: String = "${GOALS}.${HOME}"
const val GOALS_AWAY: String = "${GOALS}.${AWAY}"
const val SCORE_EXTRA_TIME_HOME: String = "${SCORE}.${EXTRA_TIME}.${HOME}"
const val SCORE_EXTRA_TIME_AWAY: String = "${SCORE}.${EXTRA_TIME}.${AWAY}"
const val SCORE_FULL_TIME_HOME: String = "${SCORE}.${FULL_TIME}.${HOME}"
const val SCORE_FULL_TIME_AWAY: String = "${SCORE}.${FULL_TIME}.${AWAY}"
const val SCORE_HALF_TIME_HOME: String = "${SCORE}.${HALF_TIME}.${HOME}"
const val SCORE_HALF_TIME_AWAY: String = "${SCORE}.${HALF_TIME}.${AWAY}"
const val SCORE_PENALTY_HOME: String = "${SCORE}.${PENALTY}.${HOME}"
const val SCORE_PENALTY_AWAY: String = "${SCORE}.${PENALTY}.${AWAY}"
const val LEAGUE_ID: String = "${LEAGUE}.${ID}"
const val TEAMS_HOME_ID: String = "${TEAMS}.${HOME}.${ID}"
const val TEAMS_HOME_LOGO: String = "${TEAMS}.${HOME}.${LOGO}"
const val TEAMS_HOME_NAME: String = "${TEAMS}.${HOME}.${NAME}"
const val TEAMS_HOME_WINNER: String = "${TEAMS}.${HOME}.${WINNER}"
const val TEAMS_AWAY_ID: String = "${TEAMS}.${AWAY}.${ID}"
const val TEAMS_AWAY_LOGO: String = "${TEAMS}.${AWAY}.${LOGO}"
const val TEAMS_AWAY_NAME: String = "${TEAMS}.${AWAY}.${NAME}"
const val TEAMS_AWAY_WINNER: String = "${TEAMS}.${AWAY}.${WINNER}"




