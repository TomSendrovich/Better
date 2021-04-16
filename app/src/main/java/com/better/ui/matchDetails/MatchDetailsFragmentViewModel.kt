package com.better.ui.matchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.EventTip

class MatchDetailsFragmentViewModel : ViewModel() {
    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList

    fun updateEventTipsByFixtureId(fixtureID: Long) {
        return Repository.queryEventTipsByFixtureId(fixtureID)
    }
}
