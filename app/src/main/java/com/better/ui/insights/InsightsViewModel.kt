package com.better.ui.insights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.StatsCalculator
import com.better.model.dataHolders.EventTip

class InsightsViewModel : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = Repository.insightEventTipsList
    val pie = MutableLiveData<HashMap<String, Double>>()

    fun updateEventTipsByUserId(userID: String) {
        // TODO: 17/06/2021 we need another livedata for results, so we dont override existing list of tips
        Repository.queryEventTipsByUserId(userID, isForInsight = true)
    }

    fun getTeamsPercent() {
        val map = StatsCalculator.calculateBestTeamGuess(eventTips.value!!)
        pie.postValue(map)
    }
}
