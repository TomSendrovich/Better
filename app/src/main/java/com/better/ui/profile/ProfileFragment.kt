package com.better.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.better.R
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import com.better.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateUser(args.userId)

        viewModel.profileToShow.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                AppUtils.bindImage(view.profile_image, user.photoUrl)
                view.profile_fullName.text = user.name

                numTipsValue.text = user.eventTips.size.toString()
                numFollowersValue.text = user.followers.size.toString()
                numFollowingValue.text = user.following.size.toString()

                viewModel.updateEventTips()
            }
        })

        profileRecyclerView.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {
                }

                override fun onItemLongClick(item: EventTip): Boolean {
                    return true
                }
            })
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value
            (profileRecyclerView.adapter as EventTipAdapter).setData(list as ArrayList<EventTip>)
        })
    }
}
