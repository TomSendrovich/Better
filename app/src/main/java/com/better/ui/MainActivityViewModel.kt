package com.better.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository

class MainActivityViewModel : ViewModel() {

    val isBanned: LiveData<Boolean> = Repository.isBanned
}
