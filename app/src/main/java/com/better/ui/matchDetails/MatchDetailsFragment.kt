package com.better.ui.matchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.better.R
import com.better.ViewModelFactory
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture
import com.better.ui.MainActivity
import com.better.utils.AppUtils
import com.better.utils.DateUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_match_details.view.*


class MatchDetailsFragment : Fragment() {

    private val args by navArgs<MatchDetailsFragmentArgs>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingButton: FloatingActionButton
    private lateinit var viewModel: MatchDetailsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        val viewModelFactory = ViewModelFactory()
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(MatchDetailsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_match_details, container, false)

        (activity as MainActivity).supportActionBar?.title =
            Fixture.buildHead2HeadText(args.selectedFixture)


        val selectedFixture = args.selectedFixture


        AppUtils.bindImage(view.home_imageView, selectedFixture.home.logo)
        AppUtils.bindImage(view.away_imageView, selectedFixture.away.logo)

        view.home_textView.text = selectedFixture.home.name
        view.away_textView.text = selectedFixture.away.name

        when {
            selectedFixture.status.isDone() -> {
                view.topText.text = Fixture.buildScoreText(selectedFixture)
                view.bottomText.text = selectedFixture.status.short
            }
            selectedFixture.status.isActive() -> {
                view.topText.text = Fixture.buildScoreText(selectedFixture)
                view.bottomText.text = Fixture.buildTimeText(selectedFixture)
            }
            selectedFixture.status.isNotStarted() -> {
                val weekDay = DateUtils.getWeekDayFromCalendar(selectedFixture.getCalendar())
                val hour = DateUtils.getHourFromCalendar(selectedFixture.getCalendar())
                view.topText.text = weekDay
                view.bottomText.text = hour
            }
            else -> {
                view.topText.visibility = View.GONE
                view.bottomText.text = selectedFixture.status.short
            }
        }

        recyclerView = view.findViewById(R.id.recycler_view_match)
        floatingButton = view.findViewById(R.id.floatingActionButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {
                }
            })
            layoutManager = LinearLayoutManager(context)
        }

        floatingButton.setOnClickListener {
            val action =
                MatchDetailsFragmentDirections.actionMatchDetailsFragmentToAddTipFragment(args.selectedFixture)
            view.findNavController().navigate(action)
        }

        viewModel.updateEventTipsByFixtureId(args.selectedFixture.id)
        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value
            (recyclerView.adapter as EventTipAdapter).setData(list as ArrayList<EventTip>)

        })
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: ")
        super.onResume()
    }

    override fun onStart() {
        Log.d(TAG, "onStart: ")
        super.onStart()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}
