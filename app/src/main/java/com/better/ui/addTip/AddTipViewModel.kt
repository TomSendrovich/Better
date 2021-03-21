package com.better.ui.addTip

import androidx.lifecycle.ViewModel
import com.better.R.string.*
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class AddTipViewModel(private val repository: Repository) : ViewModel() {

    fun onClickAddTip(fixture: Fixture, description: String, chipText: String) {
        val tipValue: Long = when (chipText) {
            "1" -> 1L
            "2" -> 2L
            "X" -> 0L
            else -> 0L
        }
        createEventTipDocument(fixture, description, tipValue)
        updateEventTipsByFixtureId(fixture.id)
    }

    fun createEventTipDocument(
        fixture: Fixture, description: String, tipValue: Long
    ) {
        repository.createEventTipDocument(fixture, description, tipValue)
    }

    fun updateEventTipsByFixtureId(fixtureID: Long) {
        return repository.updateEventTipsByFixtureId(fixtureID)
    }
}
