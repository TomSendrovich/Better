package com.better.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.better.*
import com.better.Utils.toSimpleString
import com.better.model.dataHolders.*
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

object Repository {
    private const val TAG = "Repository"
    val fixtures = MutableLiveData<List<Fixture>>()

    fun getTodayFixtures() {
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_YEAR, 1)

        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)

        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, toSimpleString(today.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, toSimpleString(tomorrow.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents")
                val list: ArrayList<Fixture> = ArrayList<Fixture>()
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
                    Log.i(TAG, fixture.toString())
                    list.add(fixture)
                } // end of documents loop
                fixtures.postValue(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getLastWeekFixtures() {
        val today = Calendar.getInstance()
        val nextWeek = Calendar.getInstance()
        nextWeek.add(Calendar.DAY_OF_YEAR, 7)

        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)

        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, toSimpleString(today.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, toSimpleString(nextWeek.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents")
                val list: ArrayList<Fixture> = ArrayList<Fixture>()
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
                    Log.i(TAG, fixture.toString())
                    list.add(fixture)
                } // end of documents loop
                fixtures.postValue(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun createFixtureFromDocument(doc: QueryDocumentSnapshot): Fixture {
        Log.d(TAG, "${doc.id} => ${doc.data}")
        val id = doc[FIXTURE_ID] as Long
        val date = doc[FIXTURE_DATE] as String
        val timestamp = doc[FIXTURE_TIMESTAMP] as Long
        val timezone = doc[FIXTURE_TIMEZONE] as String
        val status = Status(
            elapsed = doc[FIXTURE_STATUS_ELAPSED] as Long?,
            long = doc[FIXTURE_STATUS_LONG] as String,
            short = doc[FIXTURE_STATUS_SHORT] as String
        )
        val goals = Goals(
            home = doc[GOALS_HOME] as Long?,
            away = doc[GOALS_AWAY] as Long?
        )
        val score = Score(
            extraTime = Goals(
                home = doc[SCORE_EXTRA_TIME_HOME] as Long?,
                away = doc[SCORE_EXTRA_TIME_AWAY] as Long?
            ),
            fullTime = Goals(
                home = doc[SCORE_FULL_TIME_HOME] as Long?,
                away = doc[SCORE_FULL_TIME_AWAY] as Long?
            ),
            halfTime = Goals(
                home = doc[SCORE_HALF_TIME_HOME] as Long?,
                away = doc[SCORE_HALF_TIME_AWAY] as Long?
            ),
            penalty = Goals(
                home = doc[SCORE_PENALTY_HOME] as Long?,
                away = doc[SCORE_PENALTY_AWAY] as Long?
            )
        )

        val league = doc[LEAGUE_ID] as Long
        val home = FixtureTeam(
            id = doc[TEAMS_HOME_ID] as Long,
            logo = doc[TEAMS_HOME_LOGO] as String,
            name = doc[TEAMS_HOME_NAME] as String,
            winner = doc[TEAMS_HOME_WINNER] as Boolean?
        )
        val away = FixtureTeam(
            id = doc[TEAMS_AWAY_ID] as Long,
            logo = doc[TEAMS_AWAY_LOGO] as String,
            name = doc[TEAMS_AWAY_NAME] as String,
            winner = doc[TEAMS_AWAY_WINNER] as Boolean?
        )

        return Fixture(
            id = id,
            date = date,
            timestamp = timestamp,
            timezone = timezone,
            status = status,
            goals = goals,
            league = league,
            score = score,
            home = home,
            away = away
        )
    }
}
