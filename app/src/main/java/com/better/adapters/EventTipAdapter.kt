package com.better.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.better.R
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture
import com.better.utils.EventTipsDiffUtil
import com.better.utils.FixtureDiffUtil
import java.util.ArrayList

class EventTipAdapter(
    private var oldList: ArrayList<EventTip>,
    private val listener: EventTipListener
) : RecyclerView.Adapter<EventTipAdapter.ViewHolder>() {

    interface EventTipListener {
        fun onItemClicked(item: EventTip)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val userProfilePic : ImageView = view.findViewById(R.id.viewHolder_tip_profile_picture)
        val tipValue : TextView = view.findViewById(R.id.viewHolder_tip_value)
        val description : TextView = view.findViewById(R.id.viewHolder_tip_description)
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
        viewHolder.description.text = eventTip.description
        viewHolder.tipValue.text = eventTip.tipValue.toString()
        //TODO add the profile picture of the user
        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(oldList[position])
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setData(newList: ArrayList<EventTip>) {
        val diffUtil = EventTipsDiffUtil(oldList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResults.dispatchUpdatesTo(this)
    }
}