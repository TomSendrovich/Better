package com.better.ui.matchDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.better.model.Repository
import com.better.model.StatsCalculator
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture
import kotlinx.coroutines.launch

class MatchDetailsFragmentViewModel : ViewModel() {
    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList
    val pie = MutableLiveData<HashMap<Long, Int>>()
    val prediction = MutableLiveData<String>()

    fun updateEventTipsByFixtureId(fixtureID: Long) {
        Repository.queryEventTipsByFixtureId(fixtureID)
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

    fun calculateGuesses() {
        val map = StatsCalculator.calculateGuesses(eventTips.value!!)
        pie.postValue(map)
    }

    fun updateModelPrediction(fixture: Fixture) {
        if (fixture.prediction == -1L) {
            viewModelScope.launch {
                val response: String = Repository.updateModelPrediction(fixture.id)
                Log.d(TAG, "id: ${fixture.id}, model prediction: $response")
                fixture.prediction = response.toLong()

                when (response) {
                    "1" -> prediction.postValue(fixture.home.name)
                    "2" -> prediction.postValue(fixture.away.name)
                    "0" -> prediction.postValue("Draw")
                }
            }
        } else {
            when (fixture.prediction) {
                1L -> prediction.postValue(fixture.home.name)
                2L -> prediction.postValue(fixture.away.name)
                0L -> prediction.postValue("Draw")
            }
        }
    }

    companion object {
        private const val TAG = "MatchDetailsFragmentVM"
    }
}
