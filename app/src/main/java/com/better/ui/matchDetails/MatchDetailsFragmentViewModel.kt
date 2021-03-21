package com.better.ui.matchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.EventTip

class MatchDetailsFragmentViewModel(private val repository: Repository) : ViewModel() {
    val eventTips: LiveData<List<EventTip>> = repository.feedList

    fun updateEventTipsByFixtureId(fixtureID: Long) {
        return repository.updateEventTipsByFixtureId(fixtureID)
    }
}
