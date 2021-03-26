package com.better.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.better.R
import com.better.ViewModelFactory
import com.better.adapters.EventTipAdapter
import com.better.model.dataHolders.EventTip
import com.better.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        AppUtils.bindImage(view.profile_image, viewModel.appUser.photoUrl)
        view.profile_fullName.text = viewModel.appUser.name

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numTipsValue.text = viewModel.appUser.eventTips.size.toString()
        numFollowersValue.text = viewModel.appUser.followers.size.toString()
        numFollowingValue.text = viewModel.appUser.following.size.toString()

        profileRecyclerView.apply {
            adapter = EventTipAdapter(ArrayList(), object : EventTipAdapter.EventTipListener {
                override fun onItemClicked(item: EventTip) {
                }
            })
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.updateEventTips()
        viewModel.eventTips.observe(viewLifecycleOwner, {
            val list = viewModel.eventTips.value
            (profileRecyclerView.adapter as EventTipAdapter).setData(list as ArrayList<EventTip>)
        })
    }
}
