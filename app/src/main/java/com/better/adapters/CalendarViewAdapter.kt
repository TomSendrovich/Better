package com.better.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.better.ui.matches.OneDayFragment

class CalendarViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 14

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)

        // this fragment is actually empty
        return OneDayFragment()
    }
}
