package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture
import java.util.*

class MatchesViewModel(private val repository: Repository) : ViewModel() {

    val fixtures: LiveData<HashMap<Int, List<Fixture>>> = repository.fixtures
    val monthAndYearText: LiveData<String> = repository.monthAndYearText

    fun getFixturesByDate(position: Int) {
        val date = Calendar.getInstance()
        val oneDayAfter = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, position)
        oneDayAfter.add(Calendar.DAY_OF_YEAR, position + 1)

        val list = fixtures.value?.get(date[Calendar.DAY_OF_YEAR])
        if (list != null) {
            repository.fixtures.postValue(fixtures.value)
        } else {
            date[Calendar.HOUR_OF_DAY] = date.getActualMinimum(Calendar.HOUR_OF_DAY)
            date[Calendar.MINUTE] = date.getActualMinimum(Calendar.MINUTE)
            date[Calendar.SECOND] = date.getActualMinimum(Calendar.SECOND)
            date[Calendar.MILLISECOND] = date.getActualMinimum(Calendar.MILLISECOND)

            oneDayAfter[Calendar.HOUR_OF_DAY] = oneDayAfter.getActualMinimum(Calendar.HOUR_OF_DAY)
            oneDayAfter[Calendar.MINUTE] = oneDayAfter.getActualMinimum(Calendar.MINUTE)
            oneDayAfter[Calendar.SECOND] = oneDayAfter.getActualMinimum(Calendar.SECOND)
            oneDayAfter[Calendar.MILLISECOND] = oneDayAfter.getActualMinimum(Calendar.MILLISECOND)

            repository.getFixturesByDate(date, oneDayAfter)
        }
    }

    fun updateMonthAndYearText(position: Int) {
        repository.updateMonthAndYearText(position)
    }
}
