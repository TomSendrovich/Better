package com.better.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.better.*
import com.better.model.dataHolders.*
import com.better.utils.DateUtils
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

object Repository {
    private const val TAG = "Repository"
    val fixtures = MutableLiveData<List<Fixture>>()
    val feedList = MutableLiveData<List<EventTip>>()
    lateinit var appUser: AppUser

    fun getTodayFixtures() {
        val today = Calendar.getInstance()
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_YEAR, 1)

        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)

        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(today.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(tomorrow.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents")
                val list: ArrayList<Fixture> = ArrayList<Fixture>()
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
//                    Log.i(TAG, fixture.toString())
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
        val lastWeek = Calendar.getInstance()
        lastWeek.add(Calendar.DAY_OF_YEAR, -7)

        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)

        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(lastWeek.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(today.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents")
                val list: ArrayList<Fixture> = ArrayList<Fixture>()
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
//                    Log.i(TAG, fixture.toString())
                    list.add(fixture)
                } // end of documents loop
                fixtures.postValue(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun getNextWeekFixtures() {
        val today = Calendar.getInstance()
        val nextWeek = Calendar.getInstance()
        nextWeek.add(Calendar.DAY_OF_YEAR, 7)

        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)

        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(today.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(nextWeek.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents")
                val list: ArrayList<Fixture> = ArrayList<Fixture>()
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
//                    Log.i(TAG, fixture.toString())
                    list.add(fixture)
                } // end of documents loop
                fixtures.postValue(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun createFixtureFromDocument(doc: QueryDocumentSnapshot): Fixture {
//        Log.d(TAG, "${doc.id} => ${doc.data}")
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

    fun loadUser(currentUser: FirebaseUser) {
        appUser = AppUser(
            name = currentUser.displayName,
            photoUrl = currentUser.photoUrl?.toString(),
            email = currentUser.email,
            uid = currentUser.uid,
            eventTips = arrayListOf(),
        )

        // query user from DB
        val usersRef = Firebase.firestore.collection(DB_COLLECTION_USERS)

        usersRef
            .whereEqualTo(UID, currentUser.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user: ", exception)
            }.addOnCompleteListener { data ->
                Log.d(TAG, "queryUser: completed with ${data.result?.size()} results")
                if (data.result?.size() == 0) {
                    createNewUser()
                }
            }
    }

    /**
     * create new user document and save in firestore.
     * the data is taken from the user reference.
     *
     * we store only the uid of the user (unique id given by google), because the user display name
     * and user profile picture can be changed. we can get the from the user reference.
     *
     * we do not set an empty list of eventTips at the point.
     *
     * @see appUser
     */
    private fun createNewUser() {
        val newUser = hashMapOf(
            UID to appUser.uid
        )

        val usersRef = Firebase.firestore.collection(DB_COLLECTION_USERS)

        usersRef
            .document(appUser.uid)
            .set(newUser)
            .addOnSuccessListener {
                Log.i(
                    TAG,
                    "createNewUser: document ${appUser.uid} was created for user ${appUser.name}"
                )
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error creating user: ", exception)
            }
    }
}
