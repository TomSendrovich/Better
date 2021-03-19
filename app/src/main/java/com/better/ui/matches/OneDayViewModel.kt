package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture
import java.util.*

class OneDayViewModel(private val repository: Repository) : ViewModel() {

    val fixtures: LiveData<HashMap<Int, List<Fixture>>> = repository.fixtures
}
