package com.better.ui.matches

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.better.*
import com.better.adapters.CalendarViewAdapter
import com.better.adapters.FixtureAdapter
import com.better.adapters.FixtureAdapter.FixtureListener
import com.better.model.dataHolders.Fixture
import com.better.utils.DateUtils.getWeekDayAndDateFromCalendar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MatchesFragment"
private const val NUM_OF_DAYS = 14
private lateinit var viewModel: MatchesViewModel

class MatchesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var calendarViewAdapter: CalendarViewAdapter
    private lateinit var monthAndYearText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MatchesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        monthAndYearText = view.findViewById(R.id.month_and_year_text)

        setViewPager()

        //init tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, position - PAGE_SELECTED_DEFAULT)
            tab.text = getWeekDayAndDateFromCalendar(date)
        }.attach()

        viewModel.monthAndYearText.observe(viewLifecycleOwner, {
            monthAndYearText.text = it
        })
    }

    /**
     * this function set the view pager.
     *
     * 1. we create a listener for handling a page selection (tab click)
     * 2. we registering this listener
     * 3. we set the current item to be the middle
     * 4. we invoking the onPageSelected method on that item
     */
    private fun setViewPager() {
        calendarViewAdapter = CalendarViewAdapter(this, NUM_OF_DAYS)
        viewPager.adapter = calendarViewAdapter

        val pageChangeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected: $position")
                super.onPageSelected(position)
                viewModel.getFixturesByDate(position - PAGE_SELECTED_DEFAULT)
                viewModel.updateMonthAndYearText(position - PAGE_SELECTED_DEFAULT)

                setSharedPrefPageSelected(position)
            }
        }

        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        val pageSelected = getSharedPrefPageSelected()
        viewPager.currentItem = pageSelected
    }

    private fun getSharedPrefPageSelected(): Int {
        val preferences = activity?.getSharedPreferences(VIEW_PAGER, MODE_PRIVATE)
        val pageSelected = preferences?.getInt(
            SHARED_PREF_PAGE_SELECTED, PAGE_SELECTED_DEFAULT
        )

        return pageSelected ?: PAGE_SELECTED_DEFAULT
    }

    private fun setSharedPrefPageSelected(pageSelected: Int) {
        val preferences = activity?.getSharedPreferences(VIEW_PAGER, MODE_PRIVATE)
        val editor = preferences?.edit()
        editor?.putInt(SHARED_PREF_PAGE_SELECTED, pageSelected)
        editor?.apply()
    }
}

class OneDayFragment : Fragment() {

    private lateinit var noMatchesText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            val position: Int = getInt(ARG_POSITION)
            Log.d(TAG, "onViewCreated: $position")

            noMatchesText = view.findViewById(R.id.no_matches_text)

            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.apply {
                adapter = FixtureAdapter(ArrayList(), object : FixtureListener {
                    override fun onItemClicked(item: Fixture) {
                        Log.d(TAG, "onItemClicked: ${item.home.name} - ${item.away.name}")

                        val action =
                            MatchesFragmentDirections.actionNavMatchesToMatchDetailsFragment(item)

                        activity
                            ?.findNavController(R.id.nav_host_fragment)
                            ?.navigate(action)
                    }
                })
                layoutManager = LinearLayoutManager(context)
            }

            viewModel.fixtures.observe(viewLifecycleOwner, {
                val date = Calendar.getInstance()
                date.add(Calendar.DAY_OF_YEAR, position - PAGE_SELECTED_DEFAULT)

                val list = it[date[Calendar.DAY_OF_YEAR]]
                if (list != null) {
                    (recyclerView.adapter as FixtureAdapter).setData(list as ArrayList<Fixture>)

                    noMatchesText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            })

        }
    }
}
