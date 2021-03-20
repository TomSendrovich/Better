package com.better

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.better.model.Repository
import com.better.ui.about.AboutViewModel
import com.better.ui.addTip.AddTipViewModel
import com.better.ui.feed.FeedViewModel
import com.better.ui.matches.MatchesViewModel
import com.better.ui.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    val repository = Repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(FeedViewModel::class.java) -> {
                return FeedViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MatchesViewModel::class.java) -> {
                return MatchesViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> {
                return AboutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddTipViewModel::class.java)->{
                return AddTipViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("ViewModel class not found.")
    }
}
