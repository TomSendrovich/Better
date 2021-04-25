package com.better.ui.matchDetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.better.MENU_BAN
import com.better.MENU_DELETE
import com.better.R
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture
import com.better.ui.MainActivity
import com.better.utils.AppUtils
import com.better.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_match_details.*
import kotlinx.android.synthetic.main.fragment_match_details.view.*
import kotlinx.android.synthetic.main.fragment_profile.*


class MatchDetailsFragment : Fragment() {

    private val args by navArgs<MatchDetailsFragmentArgs>()
    private lateinit var viewModel: MatchDetailsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MatchDetailsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewMatch.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {
                    val action =
                        MatchDetailsFragmentDirections.actionMatchDetailsFragmentToNavProfile(item.userID)
                    view.findNavController().navigate(action)
                }

                override fun onItemRemoveClicked(item: EventTip) = showDeleteItemDialog(item)
                override fun onUserBanClicked(userID: String) = showBanUserDialog(userID)
            })
            layoutManager = LinearLayoutManager(context)
        }

        floatingActionButton.setOnClickListener {
            val action =
                MatchDetailsFragmentDirections.actionMatchDetailsFragmentToAddTipFragment(args.selectedFixture)
            view.findNavController().navigate(action)
        }

        viewModel.updateEventTipsByFixtureId(args.selectedFixture.id)
        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value ?: emptyList()
            (recyclerViewMatch.adapter as EventTipAdapter).setData(list)

            /*
            notifyDataSetChanged, to fix a bug when the item positions are wrong when navigating
            to this fragment from profile fragment (pressed back button)
            */
            (recyclerViewMatch.adapter as EventTipAdapter).notifyDataSetChanged()
        })
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_DELETE -> (profileRecyclerView.adapter as EventTipAdapter).removeItem(item.groupId)
            MENU_BAN -> (profileRecyclerView.adapter as EventTipAdapter).banUser(item.groupId)
        }
        return true
    }

    private fun showDeleteItemDialog(item: EventTip) {
        if (viewModel.isAdmin()) {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Delete Tip")
            alert.setMessage("Are you sure you want to delete?")

            alert.setPositiveButton("Yes") { _, _ ->
                viewModel.deleteEventTip(item)
                viewModel.updateEventTipsByFixtureId(item.fixtureID)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }

            alert.setNegativeButton("No") { _, _ -> }

            alert.show()
        }
    }

    private fun showBanUserDialog(userID: String) {
        if (viewModel.isAdmin()) {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Ban User")
            alert.setMessage("Are you sure you want to ban this user?")

            alert.setPositiveButton("Yes") { _, _ ->
                viewModel.banUser(userID)

                Toast.makeText(requireContext(), "Banned", Toast.LENGTH_SHORT).show()
            }

            alert.setNegativeButton("No") { _, _ -> }

            alert.show()
        }
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}
