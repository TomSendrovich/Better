package com.better.ui.feed

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.better.R
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.queryFeedEventTips()

        initRecyclerView(view)
        initObservers()
    }

    private fun initRecyclerView(view: View) {
        recyclerViewFeed.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {
                    val action =
                        FeedFragmentDirections.actionNavFeedToNavProfile(item.userID)
                    view.findNavController().navigate(action)
                }

                override fun onItemRemoveClicked(item: EventTip) {
                    showDeleteItemDialog(item)
                }

                override fun onUserBanClicked(userID: String) {
                    showBanUserDialog(userID)
                }

                override fun onInsightsClicked(item: EventTip) {
                    TODO("Not yet implemented")
                }
            })
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initObservers() {
        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value ?: emptyList()
            (recyclerViewFeed.adapter as EventTipAdapter).setData(list)

            /*
            notifyDataSetChanged, to fix a bug when the item positions are wrong when navigating
            to this fragment from profile fragment (pressed back button)
            */
            (recyclerViewFeed.adapter as EventTipAdapter).notifyDataSetChanged()
        })
    }

    private fun showDeleteItemDialog(item: EventTip) {
        if (viewModel.isAdmin()) {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Delete Tip")
            alert.setMessage("Are you sure you want to delete?")

            alert.setPositiveButton("Yes") { _, _ ->
                viewModel.deleteEventTip(item)
                viewModel.updateEventTips()
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


}
