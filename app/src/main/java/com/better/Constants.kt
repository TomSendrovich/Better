package com.better

// Intent Constants
const val EXTRA_LOGOUT = "logout"

const val ARG_POSITION = "position"
const val SHARED_PREF_PAGE_SELECTED = "pageSelected"
const val VIEW_PAGER = "viewPager"

const val PAGE_SELECTED_DEFAULT = 7
const val EVENT_TIPS_QUERY_LIMIT = 50L

const val BASE_URL = "https://us-central1-better-gsts.cloudfunctions.net"

// League IDs
const val PL = 39L
const val PD = 140L

// Admin Options
const val MENU_DELETE = 1
const val MENU_BAN = 2
const val MENU_INSIGHTS = 3

const val DB_COLLECTION_FIXTURES: String = "fixtures"
const val DB_COLLECTION_LEAGUES: String = "leagues"
const val DB_COLLECTION_TEAMS: String = "teams"
const val DB_COLLECTION_USERS: String = "users"
const val DB_COLLECTION_VENUES: String = "venues"
const val DB_COLLECTION_EVENT_TIPS: String = "eventTips"
const val DB_COLLECTION_BLACKLIST: String = "blacklist"

const val FIXTURE: String = "fixture"
const val ID: String = "id"
const val UID: String = "uid"
const val IS_ADMIN: String = "isAdmin"
const val FOLLOWERS: String = "followers"
const val FOLLOWING: String = "following"
const val EVENT_TIPS: String = "eventTips"
const val SUCC_TIPS: String = "succTips"
const val DATE: String = "date"
const val TIMESTAMP: String = "timestamp"
const val TIMEZONE: String = "timezone"
const val PREDICTION: String = "prediction"
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
const val EMAIL: String = "email"
const val USER_NAME: String = "userName"
const val PHOTO_URL: String = "photoUrl"
const val WINNER: String = "winner"
const val DESCRIPTION: String = "description"
const val TIP_VALUE: String = "tipValue"
const val IS_HIT: String = "isHit"
const val CREATED: String = "created"
const val BANNED_USERS: String = "bannedUsers"
const val USER_PIC: String = "userPic"
const val HOME_NAME: String = "homeName"
const val AWAY_NAME: String = "awayName"
const val HOME_LOGO: String = "homeLogo"
const val AWAY_LOGO: String = "awayLogo"

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
const val LEAGUE_NAME: String = "${LEAGUE}.${NAME}"
const val LEAGUE_LOGO: String = "${LEAGUE}.${LOGO}"
const val TEAMS_HOME_ID: String = "${TEAMS}.${HOME}.${ID}"
const val TEAMS_HOME_LOGO: String = "${TEAMS}.${HOME}.${LOGO}"
const val TEAMS_HOME_NAME: String = "${TEAMS}.${HOME}.${NAME}"
const val TEAMS_HOME_WINNER: String = "${TEAMS}.${HOME}.${WINNER}"
const val TEAMS_AWAY_ID: String = "${TEAMS}.${AWAY}.${ID}"
const val TEAMS_AWAY_LOGO: String = "${TEAMS}.${AWAY}.${LOGO}"
const val TEAMS_AWAY_NAME: String = "${TEAMS}.${AWAY}.${NAME}"
const val TEAMS_AWAY_WINNER: String = "${TEAMS}.${AWAY}.${WINNER}"




