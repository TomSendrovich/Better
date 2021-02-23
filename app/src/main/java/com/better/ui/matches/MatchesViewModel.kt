package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class MatchesViewModel() : ViewModel() {

    val fixtures: LiveData<List<Fixture>> = Repository.fixtures

    fun demoClick(){
        Repository.getLastWeekFixtures()
    }
}
