package com.better.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.AppUser
import com.better.model.dataHolders.EventTip

class ProfileViewModel : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList
    val profileToShow: LiveData<AppUser> = Repository.profileToShow

    fun updateEventTips() {
        return Repository.queryEventTipsByUserId(this.profileToShow.value!!.uid)
    }

    fun updateUser(userID: String) {
        if (userID.isEmpty()) {
            Repository.profileToShow.postValue(Repository.appUser.value)
        } else {
            Repository.queryUserById(userID)
        }
    }

    fun isAdmin(): Boolean {
        return Repository.appUser.value?.isAdmin ?: false
    }

    fun deleteEventTip(item: EventTip) {
        Repository.deleteEventTip(item)
    }

    fun banUser(userID: String) {
        Repository.banUser(userID)
    }
}
