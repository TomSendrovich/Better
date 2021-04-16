package com.better.model.dataHolders

/**
 * Represents a user.
 * @property uid id of the user given by google (unique)
 * @property name full name of the user
 * @property email email of the user
 * @property photoUrl link to profile picture of the user
 * @property eventTips list of ids of all event tips the user posted
 * @property succTips number of successful tips
 * @property followers list of ids of all users following the appUser
 * @property following list of ids of all users and appUser is following
 */
data class AppUser(
    val uid: String,
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    var eventTips: List<String>,
    var succTips: Long,
    var followers: List<String>,
    var following: List<String>,
    var isAdmin: Boolean
) {
    constructor(uid: String, name: String?, email: String?, photoUrl: String?) : this(
        uid = uid,
        name = name,
        email = email,
        photoUrl = photoUrl,
        eventTips = emptyList<String>(),
        succTips = 0,
        followers = emptyList<String>(),
        following = emptyList<String>(),
        isAdmin = false
    )
}
