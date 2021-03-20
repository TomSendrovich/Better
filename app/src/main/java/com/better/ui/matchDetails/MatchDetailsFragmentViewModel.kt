package com.better.ui.matchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture
import java.util.HashMap

class MatchDetailsFragmentViewModel(private val repository: Repository) : ViewModel() {
    val eventTips:LiveData<List<EventTip>> = repository.feedList
    fun getEventTipsByFixtureId(fixtureID : Long){
        return repository.getEventTipsByFixtureId(fixtureID)
    }
}