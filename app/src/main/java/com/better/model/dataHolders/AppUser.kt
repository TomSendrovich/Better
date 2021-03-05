package com.better.model.dataHolders

import android.net.Uri

data class AppUser(
    val name: String?,
    val email: String?,
    val photoUrl: Uri?,
    val uid: String
)
