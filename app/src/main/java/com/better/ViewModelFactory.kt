package com.better

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.better.model.Repository
import com.better.ui.about.AboutViewModel
import com.better.ui.feed.FeedViewModel
import com.better.ui.matches.MatchesViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(FeedViewModel::class.java) -> {
                return FeedViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MatchesViewModel::class.java) -> {
                return MatchesViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> {
                return FeedViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("ViewModel class not found.")
    }
}
