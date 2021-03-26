package com.better.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.AppUser
import com.better.model.dataHolders.EventTip

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = repository.eventTipsList
    val appUser: AppUser = repository.appUser

    fun updateEventTips() {
        return repository.queryEventTipsByUserId(appUser.uid)
    }
}
