package com.better.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.EventTip

class FeedViewModel : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList

    fun updateEventTips() {
        Repository.queryFeedEventTips()
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

    fun queryFeedEventTips(){
        Repository.queryFeedEventTips()
    }
}
