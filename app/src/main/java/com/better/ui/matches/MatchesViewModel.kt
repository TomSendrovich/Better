package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class MatchesViewModel(private val repository: Repository) : ViewModel() {

    val fixtures: LiveData<List<Fixture>> = repository.fixtures
}
