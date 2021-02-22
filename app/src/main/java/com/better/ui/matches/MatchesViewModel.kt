package com.better.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.Fixture

class MatchesViewModel() : ViewModel() {

    val fixtures: LiveData<List<Fixture>> = Repository.fixtures

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is matches Fragment"
//    }
//    val text: LiveData<String> = _text

    fun demoClick(){
        Repository.getLastWeekFixtures()
    }
}
