package com.better.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.better.ARG_POSITION
import com.better.ui.matches.OneDayFragment

class CalendarViewAdapter(fragment: Fragment, private val numOfItems: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = numOfItems

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = OneDayFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_POSITION, position)
        }

        return fragment
    }
}
