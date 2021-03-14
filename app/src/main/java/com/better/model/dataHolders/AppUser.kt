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
    val eventTips: List<String>,
    val succTips: Long,
    val followers: List<String>,
    val following: List<String>
) {
    constructor(uid: String, name: String?, email: String?, photoUrl: String?) : this(
        uid, name, email, photoUrl, emptyList<String>(), 0, emptyList<String>(), emptyList<String>()
    )
}
