package com.better.ui.profile

import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.dataHolders.AppUser

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    val appUser: AppUser = repository.appUser
}
