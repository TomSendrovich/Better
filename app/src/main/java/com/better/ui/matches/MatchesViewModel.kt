package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture
import java.util.*

class MatchesViewModel : ViewModel() {

    val fixtures: LiveData<HashMap<Int, List<Fixture>>> = Repository.fixtures
    val monthAndYearText: LiveData<String> = Repository.monthAndYearText

    fun getFixturesByDate(position: Int) {
        val date = Calendar.getInstance()
        val oneDayAfter = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, position)
        oneDayAfter.add(Calendar.DAY_OF_YEAR, position + 1)

        val list = fixtures.value?.get(date[Calendar.DAY_OF_YEAR])
        if (list != null) {
            Repository.fixtures.postValue(fixtures.value)
        } else {
            date[Calendar.HOUR_OF_DAY] = date.getActualMinimum(Calendar.HOUR_OF_DAY)
            date[Calendar.MINUTE] = date.getActualMinimum(Calendar.MINUTE)
            date[Calendar.SECOND] = date.getActualMinimum(Calendar.SECOND)
            date[Calendar.MILLISECOND] = date.getActualMinimum(Calendar.MILLISECOND)

            oneDayAfter[Calendar.HOUR_OF_DAY] = oneDayAfter.getActualMinimum(Calendar.HOUR_OF_DAY)
            oneDayAfter[Calendar.MINUTE] = oneDayAfter.getActualMinimum(Calendar.MINUTE)
            oneDayAfter[Calendar.SECOND] = oneDayAfter.getActualMinimum(Calendar.SECOND)
            oneDayAfter[Calendar.MILLISECOND] = oneDayAfter.getActualMinimum(Calendar.MILLISECOND)

            Repository.queryFixturesByDate(date, oneDayAfter)
        }
    }

    fun updateMonthAndYearText(position: Int) {
        Repository.updateMonthAndYearText(position)
    }
}
