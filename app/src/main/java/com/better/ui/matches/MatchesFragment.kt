package com.better.ui.matches

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
import com.better.R
import com.better.ViewModelFactory
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
private const val SEVEN_DAYS = 7

class MatchesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var calendarViewAdapter: CalendarViewAdapter
    private lateinit var monthAndYearText: TextView
    private lateinit var noMatchesText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MatchesViewModel

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
        recyclerView = view.findViewById(R.id.recycler_view)
        monthAndYearText = view.findViewById(R.id.month_and_year_text)
        noMatchesText = view.findViewById(R.id.no_matches_text)

        setViewPager()

        //init tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, position - SEVEN_DAYS)
            tab.text = getWeekDayAndDateFromCalendar(date)
        }.attach()


        recyclerView.apply {
            adapter = FixtureAdapter(ArrayList(), object : FixtureListener {
                override fun onItemClicked(item: Fixture) {
                    Log.d(TAG, "onItemClicked: ${item.home.name} - ${item.away.name}")

                    val action = MatchesFragmentDirections
                        .actionNavMatchesToMatchDetailsFragment(item)

                    activity
                        ?.findNavController(R.id.nav_host_fragment)
                        ?.navigate(action)
                }
            })
            layoutManager = LinearLayoutManager(context)
        }

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
        calendarViewAdapter = CalendarViewAdapter(this)
        viewPager.adapter = calendarViewAdapter

        val pageChangeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected: ")
                super.onPageSelected(position)
                viewModel.getFixturesByDate(position - SEVEN_DAYS)
                viewModel.updateMonthAndYearText(position - SEVEN_DAYS)
                viewModel.fixtures.observe(viewLifecycleOwner, {
                    val date = Calendar.getInstance()
                    date.add(Calendar.DAY_OF_YEAR, position - SEVEN_DAYS)

                    val list = it[date[Calendar.DAY_OF_YEAR]]
                    if (list != null) {
                        (recyclerView.adapter as FixtureAdapter).setData(list as ArrayList<Fixture>)

                        noMatchesText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    }
                })
            }
        }
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
        viewPager.currentItem = (viewPager.adapter as CalendarViewAdapter).itemCount / 2
        pageChangeCallback.onPageSelected(viewPager.currentItem)
    }
}

class OneDayFragment : Fragment()
