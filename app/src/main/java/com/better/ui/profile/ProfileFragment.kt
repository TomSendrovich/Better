package com.better.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.better.*
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import com.better.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var followBtn: Button
    private lateinit var unFollowBtn: Button
    private lateinit var viewAction: View
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        followBtn = view.findViewById(R.id.followBtn)
        unFollowBtn = view.findViewById(R.id.unFollowBtn)
        viewAction = view.findViewById(R.id.view_buttons)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers(view)
        addEventListeners()
        viewModel.updateUser(args.userId)
    }

    private fun addEventListeners() {
        followBtn.setOnClickListener { viewModel.onFollowBtnClicked() }
        unFollowBtn.setOnClickListener { viewModel.onUnFollowBtnClicked() }
    }

    private fun initObservers(view: View) {
        viewModel.profileToShow.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                AppUtils.bindImageCrop(view.profile_image, user.photoUrl)
                view.profile_fullName.text = user.name

                numTipsValue.text = user.eventTips.size.toString()
                numFollowersValue.text = user.followers.size.toString()
                numFollowingValue.text = user.following.size.toString()

                viewModel.updateEventTips()

                if (viewModel.isMyProfile()) {
                    viewAction.visibility = View.GONE
                    followBtn.visibility = View.GONE
                    unFollowBtn.visibility = View.GONE
                } else {
                    viewAction.visibility = View.VISIBLE

                    // we display another user profile. handle which btn to display
                    if (viewModel.isFollowingUser()) {
                        followBtn.visibility = View.GONE
                        unFollowBtn.visibility = View.VISIBLE
                    } else {
                        followBtn.visibility = View.VISIBLE
                        unFollowBtn.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value
            (profileRecyclerView.adapter as EventTipAdapter).setData(list as ArrayList<EventTip>)

            viewModel.calculateHits()
        })

        viewModel.stats.observe(viewLifecycleOwner, { map ->
            if (map.isNotEmpty()) {
                view.hit_pl.text = "Hit: " + (map[PL]?.get(0) ?: 0).toString()
                view.miss_pl.text = "Miss: " + (map[PL]?.get(1) ?: 0).toString()

                view.hit_pd.text = "Hit: " + (map[PD]?.get(0) ?: 0).toString()
                view.miss_pd.text = "Miss: " + (map[PD]?.get(1) ?: 0).toString()

//                Toast.makeText(context, "calculateStats Done!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.leagues.observe(viewLifecycleOwner, { list ->
            list.forEach { league ->
                when (league.id) {
                    39L -> AppUtils.bindImage(view.logo_pl, league.logo)
                    140L -> AppUtils.bindImage(view.logo_pd, league.logo)
                }
            }

//            Toast.makeText(context, "leagues Done!", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerView() {
        profileRecyclerView.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {}
                override fun onItemRemoveClicked(item: EventTip) = showDeleteItemDialog(item)
                override fun onUserBanClicked(userID: String) = showBanUserDialog(userID)
                override fun onInsightsClicked(item: EventTip) {
                    TODO("Not yet implemented")
                }
            })
            layoutManager = LinearLayoutManager(context)
        }
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
                viewModel.updateEventTips()

                //decrement tipCount of user by 1
                numTipsValue.text = numTipsValue.text.toString().toInt().dec().toString()

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
