package com.better.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.better.R
import com.better.model.dataHolders.EventTip
import com.better.utils.AppUtils
import com.better.utils.EventTipsDiffUtil

class EventTipAdapter(
    private var oldList: List<EventTip>,
    private val listener: EventTipListener
) : RecyclerView.Adapter<EventTipAdapter.ViewHolder>() {

    interface EventTipListener {
        fun onItemClicked(item: EventTip)
        fun onItemLongClick(item: EventTip): Boolean
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userProfilePic: ImageView = view.findViewById(R.id.viewHolder_tip_profile_picture)
        val tipValue: TextView = view.findViewById(R.id.viewHolder_tip_value)
        val description: TextView = view.findViewById(R.id.viewHolder_tip_description)
        val matchName: TextView = view.findViewById(R.id.viewHolder_match_name)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_holder_tip, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val eventTip = oldList[position]

        if (eventTip.description.isNullOrEmpty()) {
            viewHolder.description.visibility = View.GONE
            viewHolder.description.text = eventTip.description
        } else {
            viewHolder.description.visibility = View.VISIBLE
            viewHolder.description.text = eventTip.description
        }

        AppUtils.bindImage(viewHolder.userProfilePic, eventTip.userPic)
        viewHolder.matchName.text = "${eventTip.homeName} - ${eventTip.awayName}"

        when (eventTip.tipValue) {
            1L -> viewHolder.tipValue.text = "Tip: Home Wins"
            2L -> viewHolder.tipValue.text = "Tip: Away Wins"
            0L -> viewHolder.tipValue.text = "Tip: Draw"
        }

        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(oldList[position])
        }
        viewHolder.itemView.setOnLongClickListener {
            listener.onItemLongClick(oldList[position])
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setData(newList: List<EventTip>) {
        val diffUtil = EventTipsDiffUtil(oldList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}
