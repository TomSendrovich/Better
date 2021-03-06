package com.better.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.better.*
import com.better.api.RetrofitInstance
import com.better.model.dataHolders.*
import com.better.utils.DateUtils
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

object Repository {
    private const val TAG = "Repository"

    val fixtures = MutableLiveData<HashMap<Int, List<Fixture>>>()
    val eventTipsList = MutableLiveData<List<EventTip>>()
    val insightEventTipsList = MutableLiveData<List<EventTip>>()
    val monthAndYearText = MutableLiveData<String>()
    val isBanned = MutableLiveData<Boolean>()
    val appUser = MutableLiveData<AppUser>()
    val profileToShow = MutableLiveData<AppUser>()
    val leagues = MutableLiveData<List<League>>()

    init {
        fixtures.value = HashMap<Int, List<Fixture>>()
    }

    suspend fun updateModelPrediction(id: Long): Response<String> {
        return RetrofitInstance.API.updateModelPrediction(id)
    }

    //region Query from firestore
    fun queryFixturesByDate(from: Calendar, to: Calendar) {
        val fixturesRef = Firebase.firestore.collection(DB_COLLECTION_FIXTURES)
        fixturesRef
            .whereGreaterThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(from.time))
            .whereLessThanOrEqualTo(FIXTURE_DATE, DateUtils.toSimpleString(to.time))
            .get()
            .addOnSuccessListener { documents ->
                Log.i(TAG, "queried ${documents.size()} documents (Fixtures)")
                val list: ArrayList<Fixture> = ArrayList()
                val map: HashMap<Int, List<Fixture>> = fixtures.value!!
                for (doc in documents) {
                    val fixture = createFixtureFromDocument(doc)
                    list.add(fixture)
                } // end of documents loop
                map[from[Calendar.DAY_OF_YEAR]] = list
                updateFixturesMap(map)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun queryEventTipsByFixtureId(fixtureID: Long) {
        Firebase.firestore
            .collection(DB_COLLECTION_EVENT_TIPS)
            .whereEqualTo(FIXTURE, fixtureID)
            .orderBy(CREATED, Query.Direction.DESCENDING)
            .limit(EVENT_TIPS_QUERY_LIMIT)
            .get()
            .addOnSuccessListener { documents ->
                val list: ArrayList<EventTip> = ArrayList()
                for (doc in documents) {
                    val eventTip = createEventTipFromDocument(doc)
                    list.add(eventTip)
                }
                updateEventTipsList(list, isForInsight = false)
            }
    }

    fun queryEventTipsByUserId(userID: String, isForInsight: Boolean) {
        Firebase.firestore
            .collection(DB_COLLECTION_EVENT_TIPS)
            .whereEqualTo(UID, userID)
            .orderBy(CREATED, Query.Direction.DESCENDING)
            .limit(EVENT_TIPS_QUERY_LIMIT)
            .get()
            .addOnSuccessListener { documents ->
                val list: ArrayList<EventTip> = ArrayList()
                for (doc in documents) {
                    val eventTip = createEventTipFromDocument(doc)
                    list.add(eventTip)
                }
                updateEventTipsList(list, isForInsight)
            }
    }

    fun queryFeedEventTips() {
        val following = appUser.value?.following ?: emptyList()

        if (following.isNotEmpty()) {
            Firebase.firestore
                .collection(DB_COLLECTION_EVENT_TIPS)
                .whereIn(UID, following)
                .orderBy(CREATED, Query.Direction.DESCENDING)
                .limit(EVENT_TIPS_QUERY_LIMIT)
                .get()
                .addOnSuccessListener { documents ->
                    val list: ArrayList<EventTip> = ArrayList()
                    for (doc in documents) {
                        val eventTip = createEventTipFromDocument(doc)
                        list.add(eventTip)
                    }
                    updateEventTipsList(list, isForInsight = false)
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun queryUserById(userID: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .whereEqualTo(UID, userID)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 1) {
                    Log.wtf(TAG, "query user by uid return more than one element")
                } else if (!documents.isEmpty) {
                    val userDoc = documents.first()

                    val user = AppUser(
                        uid = userDoc[UID] as String,
                        name = userDoc[NAME] as String?,
                        email = userDoc[EMAIL] as String?,
                        photoUrl = userDoc[PHOTO_URL] as String?,
                        followers = (userDoc[FOLLOWERS] ?: emptyList<String>()) as List<String>,
                        following = (userDoc[FOLLOWING] ?: emptyList<String>()) as List<String>,
                        eventTips = (userDoc[EVENT_TIPS] ?: emptyList<String>()) as List<String>,
                        succTips = (userDoc[SUCC_TIPS] ?: 0L) as Long,
                        isAdmin = (userDoc[IS_ADMIN] ?: false) as Boolean
                    )

                    profileToShow.postValue(user)
                    Log.d("profile", "queryUserById: ${user.name}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user $userID: ", exception)
            }
    }

    fun queryLeagues() {
        Firebase.firestore
            .collection(DB_COLLECTION_LEAGUES)
            .get()
            .addOnSuccessListener { documents ->
                Log.d(TAG, "queryLeagues: ${documents.size()} documents")
                val list: ArrayList<League> = ArrayList()
                for (doc in documents) {
                    val league = createLeagueFromDocument(doc)
                    list.add(league)
                }
                updateLeagueList(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting leagues", exception)
            }
    }


    @Suppress("UNCHECKED_CAST")
    fun loadUser(currentUser: FirebaseUser): AppUser? {
        appUser.postValue(
            AppUser(
                uid = currentUser.uid,
                name = currentUser.displayName,
                email = currentUser.email,
                photoUrl = currentUser.photoUrl?.toString()
            )
        )

        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .whereEqualTo(UID, currentUser.uid)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 1) {
                    Log.wtf(TAG, "query user by uid return more than one element")
                } else if (!documents.isEmpty) {
                    val userDoc = documents.first()

                    val user = appUser.value!!

                    user.followers =
                        (userDoc[FOLLOWERS] ?: emptyList<String>()) as List<String>
                    user.following =
                        (userDoc[FOLLOWING] ?: emptyList<String>()) as List<String>
                    user.eventTips =
                        (userDoc[EVENT_TIPS] ?: emptyList<String>()) as List<String>
                    user.succTips = (userDoc[SUCC_TIPS] ?: 0L) as Long
                    user.isAdmin = (userDoc[IS_ADMIN] ?: false) as Boolean

                    appUser.postValue(user)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting user ${currentUser.uid}: ", exception)
            }
            .addOnCompleteListener { data ->
                Log.d(TAG, "queryUser: completed with ${data.result?.size()} results")
                if (data.result?.size() == 0) {
                    createNewUserDocument()
                }
            }

        return null
    }

    fun isBannedUser(uid: String) {
        Firebase.firestore.collection(DB_COLLECTION_BLACKLIST)
            .document(BANNED_USERS)
            .get()
            .addOnSuccessListener { document ->
                val list = document["list"] as ArrayList<*>
                if (list.contains(uid)) {
                    isBanned.postValue(true)
                } else {
                    isBanned.postValue(false)
                }
            }
    }

//endregion

    //region Write Document to firestore

    /**
     * create new user document and save in firestore.
     * the data is taken from the user reference.
     *
     * we do not set an empty list of eventTips.
     *
     * @see appUser
     */
    private fun createNewUserDocument() {
        val newUser = hashMapOf(
            UID to appUser.value!!.uid,
            NAME to appUser.value!!.name,
            EMAIL to appUser.value!!.email,
            PHOTO_URL to appUser.value!!.photoUrl,
            CREATED to FieldValue.serverTimestamp(),
            IS_ADMIN to false
        )

        val usersRef = Firebase.firestore.collection(DB_COLLECTION_USERS)

        usersRef
            .document(appUser.value!!.uid)
            .set(newUser)
            .addOnSuccessListener {
                Log.i(
                    TAG,
                    "createNewUser: document ${appUser.value!!.uid} was created for user ${appUser.value!!.name}"
                )
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error creating user: ", exception)
            }
    }

    fun createEventTipDocument(fixture: Fixture, description: String, tipValue: Long) {
        val eventTip = hashMapOf(
            UID to appUser.value!!.uid,
            USER_NAME to appUser.value!!.name,
            USER_PIC to appUser.value!!.photoUrl,
            DESCRIPTION to description,
            HOME_NAME to fixture.home.name,
            AWAY_NAME to fixture.away.name,
            HOME_LOGO to fixture.home.logo,
            AWAY_LOGO to fixture.away.logo,
            LEAGUE to fixture.league,
            FIXTURE to fixture.id,
            TIP_VALUE to tipValue,
            CREATED to FieldValue.serverTimestamp()
        )
        Firebase.firestore.collection(DB_COLLECTION_EVENT_TIPS)
            .add(eventTip)
            .addOnSuccessListener {
                Log.i(TAG, "createEventTipDocument succeeded")
                attachEventTipToUser(it)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error creating user: ", exception)
            }
    }

    private fun attachEventTipToUser(eventTip: DocumentReference) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(appUser.value!!.uid)
            .update(EVENT_TIPS, FieldValue.arrayUnion(eventTip.id))
            .addOnSuccessListener {
                Log.i(
                    TAG,
                    "attachEventTipToUser: succeeded for uid ${appUser.value!!.uid} and eventTip ${eventTip.id}"
                )
            }
            .addOnFailureListener {
                Log.e(
                    TAG,
                    "attachEventTipToUser: failed for uid ${appUser.value!!.uid} and eventTip ${eventTip.id}"
                )
            }
    }

    fun addFollowing(userToFollow: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(appUser.value!!.uid)
            .update(FOLLOWING, FieldValue.arrayUnion(userToFollow))
            .addOnSuccessListener {
                Log.i(TAG, "followUser: succeeded to follow $userToFollow")
            }
            .addOnFailureListener {
                Log.e(TAG, "followUser: failed to follow $userToFollow")
            }
    }

    fun addFollower(userToFollow: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(userToFollow)
            .update(FOLLOWERS, FieldValue.arrayUnion(appUser.value!!.uid))
            .addOnSuccessListener {
                Log.i(TAG, "addFollower: succeeded to follow $userToFollow")
            }
            .addOnFailureListener {
                Log.e(TAG, "addFollower: failed to follow $userToFollow")
            }
    }

    fun removeFollowing(userToFollow: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(appUser.value!!.uid)
            .update(FOLLOWING, FieldValue.arrayRemove(userToFollow))
            .addOnSuccessListener {
                Log.i(TAG, "followUser: succeeded to follow $userToFollow")
            }
            .addOnFailureListener {
                Log.e(TAG, "followUser: failed to follow $userToFollow")
            }
    }

    fun removeFollower(userToFollow: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(userToFollow)
            .update(FOLLOWERS, FieldValue.arrayRemove(appUser.value!!.uid))
            .addOnSuccessListener {
                Log.i(TAG, "addFollower: succeeded to follow $userToFollow")
            }
            .addOnFailureListener {
                Log.e(TAG, "addFollower: failed to follow $userToFollow")
            }
    }

    /**
     * Ban any user from the app.
     * The actual operation is adding the uid of the user to a list.
     * The list reference is /blacklist/bannedUsers/list - array of strings.
     *
     * @param uid the id of the user
     **/
    fun banUser(uid: String) {
        Firebase.firestore.collection(DB_COLLECTION_BLACKLIST)
            .document(BANNED_USERS)
            .update("list", FieldValue.arrayUnion(uid))
            .addOnSuccessListener { Log.i(TAG, "banUser: $uid is banned successfully") }
            .addOnFailureListener { Log.e(TAG, "banUser: ban operation is failed for $uid") }
    }

//endregion

    //region Delete Documents from firestore

    fun deleteEventTip(eventTip: EventTip) {
        Firebase.firestore.collection(DB_COLLECTION_EVENT_TIPS).document(eventTip.tipID)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "EventTip successfully deleted!")
                removeEventTipFromUser(eventTip.tipID, eventTip.userID)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    private fun removeEventTipFromUser(eventTipId: String, userId: String) {
        Firebase.firestore.collection(DB_COLLECTION_USERS)
            .document(userId)
            .update("eventTips", FieldValue.arrayRemove(eventTipId))
            .addOnSuccessListener {
                Log.i(
                    TAG,
                    "removeEventTipFromUser: succeeded for uid ${appUser.value!!.uid} and eventTip $eventTipId"
                )
            }
            .addOnFailureListener {
                Log.e(
                    TAG,
                    "removeEventTipFromUser: failed for uid ${appUser.value!!.uid} and eventTip $eventTipId"
                )
            }
    }


//endregion

    //region Create Data Classes from firebase document

    private fun createFixtureFromDocument(doc: QueryDocumentSnapshot): Fixture {
//        Log.e(TAG, "${doc.id} => ${doc.data}")
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
        val prediction = (doc[PREDICTION] ?: arrayListOf(0, 0, 0)) as ArrayList<Double>

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
            away = away,
            prediction = prediction
        )
    }

    private fun createEventTipFromDocument(doc: QueryDocumentSnapshot): EventTip {
        return EventTip(
            tipID = doc.id,
            userID = doc[UID] as String,
            userName = (doc[USER_NAME] ?: "Unknown User") as String,
            userPic = doc[USER_PIC] as String,
            fixtureID = doc[FIXTURE] as Long,
            homeName = doc[HOME_NAME] as String,
            awayName = doc[AWAY_NAME] as String,
            homeLogo = doc[HOME_LOGO] as String,
            awayLogo = doc[AWAY_LOGO] as String,
            league = (doc[LEAGUE] ?: 0L) as Long,
            description = doc[DESCRIPTION] as String,
            tipValue = doc[TIP_VALUE] as Long,
            isHit = doc[IS_HIT] as Boolean?,
            created = (doc[CREATED] ?: Timestamp.now()) as Timestamp
        )
    }

    private fun createLeagueFromDocument(doc: QueryDocumentSnapshot): League {
        return League(
            id = doc[LEAGUE_ID] as Long,
            name = doc[LEAGUE_NAME] as String,
            logo = doc[LEAGUE_LOGO] as String
        )
    }

//endregion

    //region postValue MutableLiveData Objects
    fun updateMonthAndYearText(position: Int) {
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, position)

        monthAndYearText.postValue(DateUtils.getMonthAndYearFromCalendar(date))
    }

    private fun updateFixturesMap(map: HashMap<Int, List<Fixture>>) {
        fixtures.postValue(map)
    }

    private fun updateEventTipsList(list: ArrayList<EventTip>, isForInsight: Boolean) {
        if (isForInsight) {
            insightEventTipsList.postValue(list)
        } else {
            eventTipsList.postValue(list)
        }
    }

    private fun updateLeagueList(list: ArrayList<League>) {
        leagues.postValue(list)
    }
//endregion
}
