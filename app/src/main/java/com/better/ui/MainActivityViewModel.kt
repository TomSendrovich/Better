package com.better.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.AppUser

class MainActivityViewModel : ViewModel() {

    val isBanned: LiveData<Boolean> = Repository.isBanned
    val appUser: LiveData<AppUser> = Repository.appUser
}
