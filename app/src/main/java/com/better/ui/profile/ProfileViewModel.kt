package com.better.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.better.model.Repository
import com.better.model.StatsCalculator
import com.better.model.dataHolders.AppUser
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.League

class ProfileViewModel : ViewModel() {

    val eventTips: LiveData<List<EventTip>> = Repository.eventTipsList
    val leagues: LiveData<List<League>> = Repository.leagues
    val profileToShow: LiveData<AppUser> = Repository.profileToShow
    val stats = MutableLiveData<HashMap<Long, IntArray>>()

    fun updateEventTips() {
        return Repository.queryEventTipsByUserId(this.profileToShow.value!!.uid,
            isForInsight = false)
    }

    fun updateUser(userID: String) {
        if (userID.isEmpty()) {
            Repository.profileToShow.postValue(Repository.appUser.value)
        } else {
            Repository.queryUserById(userID)
        }
    }

    fun isAdmin(): Boolean {
        return Repository.appUser.value?.isAdmin ?: false
    }

    fun deleteEventTip(item: EventTip) {
        Repository.deleteEventTip(item)
    }

    fun banUser(userID: String) {
        Repository.banUser(userID)
    }

    fun isMyProfile(): Boolean {
        return profileToShow.value?.uid == Repository.appUser.value?.uid
    }

    fun isFollowingUser(): Boolean {
        return Repository.appUser.value!!.following.contains(profileToShow.value!!.uid)
    }

    fun onFollowBtnClicked() {
        addFollowingToLocalAppUser()

        Repository.addFollowing(profileToShow.value!!.uid)
        Repository.addFollower(profileToShow.value!!.uid)
        updateUser(profileToShow.value!!.uid)
    }

    fun onUnFollowBtnClicked() {
        removeFollowingFromLocalAppUser()

        Repository.removeFollowing(profileToShow.value!!.uid)
        Repository.removeFollower(profileToShow.value!!.uid)
        updateUser(profileToShow.value!!.uid)
    }

    private fun addFollowingToLocalAppUser() {
        var list = Repository.appUser.value!!.following

        if (list.isEmpty()) {
            list = ArrayList()
        }

        (list as ArrayList<String>).add(profileToShow.value!!.uid)
        Repository.appUser.postValue(Repository.appUser.value)
    }

    private fun removeFollowingFromLocalAppUser() {
        val list = Repository.appUser.value!!.following as ArrayList<String>
        list.remove(profileToShow.value!!.uid)
        Repository.appUser.postValue(Repository.appUser.value)
    }

    fun calculateHits() {
        val map = StatsCalculator.calculateHits(eventTips.value!!)
        stats.postValue(map)
    }

}
