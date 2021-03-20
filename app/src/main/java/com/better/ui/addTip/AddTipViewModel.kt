package com.better.ui.addTip

import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class AddTipViewModel(private val repository: Repository) : ViewModel() {
    fun createEventTipDocument(
        fixture: Fixture,
        description: String,
        tipValue: Long
    ){
        repository.createEventTipDocument(fixture,description,tipValue)
    }
}