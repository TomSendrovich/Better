package com.better.model.dataHolders

import android.net.Uri

/**
 * Represents a user.
 * @property name full name of the user
 * @property email email of the user
 * @property photoUrl link to profile picture of the user
 * @property uid id of the user given by google (unique)
 * @property eventTips list of all event tips of the user
 */
data class AppUser(
    val name: String?,
    val email: String?,
    val photoUrl: String?,
    val uid: String,
    val eventTips: List<EventTip>
)
