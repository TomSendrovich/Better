package com.better.ui.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.better.R
import com.better.ViewModelFactory
import com.better.adapters.FixtureAdapter
import com.better.adapters.FixtureAdapter.FixtureListener
import com.better.model.dataHolders.Fixture
import com.better.utils.DateUtils.getMonthAndYearFromCalendar
import com.better.utils.DateUtils.getWeekDayAndDateFromCalendar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

private lateinit var viewModel: MatchesViewModel
private const val TAG = "MatchesFragment"

class MatchesFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var monthAndYearText: TextView

    companion object {
    }

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

        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter
        tabLayout = view.findViewById(R.id.tab_layout)
        monthAndYearText = view.findViewById(R.id.month_and_year_text)
        monthAndYearText.text = getMonthAndYearFromCalendar(Calendar.getInstance())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, position)
            tab.text = getWeekDayAndDateFromCalendar(date)
        }.attach()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toast.makeText(context, tab.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("Selected_Page", position.toString())

                viewModel.getFixturesByDate(position)
            }

        })

    }
}

private const val ARG_POSITION = "position"

class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 100

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

class OneDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ${arguments?.get(ARG_POSITION)}")
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            Log.d(TAG, "onViewCreated: ${getInt(ARG_POSITION)}")

//            viewModel.getFixturesByDate(getInt(ARG_POSITION))

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
                date.add(Calendar.DAY_OF_YEAR, getInt(ARG_POSITION))

                val list = it[date[Calendar.DAY_OF_YEAR]]
                if (list != null) {
                    (recyclerView.adapter as FixtureAdapter).setData(list as ArrayList<Fixture>)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ${arguments?.get(ARG_POSITION)}")
    }
}
