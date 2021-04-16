package com.better.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.AppUser
import com.better.model.dataHolders.EventTip

class ProfileViewModel : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList
    val appUser: AppUser = Repository.appUser

    fun updateEventTips() {
        return Repository.queryEventTipsByUserId(appUser.uid)
    }
}
