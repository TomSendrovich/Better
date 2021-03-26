package com.better.ui.addTip

import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class AddTipViewModel(private val repository: Repository) : ViewModel() {

    fun onClickAddTip(fixture: Fixture, description: String, chipText: String) {
        val tipValue: Long = when (chipText) {
            fixture.home.name -> 1L // home wins
            fixture.away.name -> 2L // away wins
            else -> 0L // draw
        }
        createEventTipDocument(fixture, description, tipValue)
        updateEventTipsByFixtureId(fixture.id)
    }

    private fun createEventTipDocument(
        fixture: Fixture, description: String, tipValue: Long
    ) {
        repository.createEventTipDocument(fixture, description, tipValue)
    }

    private fun updateEventTipsByFixtureId(fixtureID: Long) {
        return repository.queryEventTipsByFixtureId(fixtureID)
    }
}
