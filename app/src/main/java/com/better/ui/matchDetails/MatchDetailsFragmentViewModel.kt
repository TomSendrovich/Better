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
import retrofit2.Response

class MatchDetailsFragmentViewModel : ViewModel() {
    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList
    val pie = MutableLiveData<HashMap<Long, Int>>()
    val prediction = MutableLiveData<ArrayList<Double>>()

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
        if (fixture.prediction == arrayListOf(0, 0, 0)) {
            viewModelScope.launch {
                try {
                    val response: Response<String> = Repository.updateModelPrediction(fixture.id)
                    if (response.isSuccessful) {
                        val responseBody = response.body()!!
                        Log.d(TAG, "id: ${fixture.id}, model prediction: $responseBody")
                        val predictionList =
                            (responseBody.trim('[', ']', ' ').split(' ') as ArrayList)
                        predictionList.removeIf { it.isBlank() }

                        fixture.prediction = arrayListOf(
                            predictionList[0].toDouble(),
                            predictionList[1].toDouble(),
                            predictionList[2].toDouble())
                        prediction.postValue(fixture.prediction)
                    } else {
                        Log.e(TAG, "updateModelPrediction: Error: ${response.errorBody()}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "updateModelPrediction: CATCH Error: ${e.message}")
                }

            }
        } else {
            prediction.postValue(fixture.prediction)
        }
    }

    companion object {
        private const val TAG = "MatchDetailsFragmentVM"
    }
}
