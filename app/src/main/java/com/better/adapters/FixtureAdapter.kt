package com.better.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.better.R
import com.better.model.dataHolders.Fixture
import com.better.utils.AppUtils
import com.better.utils.DateUtils
import java.util.*


class FixtureAdapter(private var list: List<Fixture>, private val listener: FixtureListener) :
    RecyclerView.Adapter<FixtureAdapter.ViewHolder>() {

    interface FixtureListener {
        fun onItemClicked(item: Fixture)
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeName: TextView = view.findViewById(R.id.viewHolder_match_home_text)
        val awayName: TextView = view.findViewById(R.id.viewHolder_match_away_text)
        val homeLogo: ImageView = view.findViewById(R.id.viewHolder_match_home_image)
        val awayLogo: ImageView = view.findViewById(R.id.viewHolder_match_away_image)
        val topText: TextView = view.findViewById(R.id.viewHolder_match_topText)
        val bottomText: TextView = view.findViewById(R.id.viewHolder_match_bottomText)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_holder_match, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val fixture = list[position]
        viewHolder.homeName.text = fixture.home.name
        viewHolder.awayName.text = fixture.away.name

        when {
            fixture.status.isDone() -> {
                viewHolder.topText.text = Fixture.buildScoreText(fixture)
                viewHolder.bottomText.text = fixture.status.short
            }
            fixture.status.isActive() -> {
                viewHolder.topText.text = Fixture.buildScoreText(fixture)
                viewHolder.bottomText.text = Fixture.buildTimeText(fixture)
            }
            fixture.status.isNotStarted() -> {
                val weekDay = DateUtils.getWeekDayFromCalendar(fixture.getCalendar())
                val hour = DateUtils.getHourFromCalendar(fixture.getCalendar())
                viewHolder.topText.text = weekDay
                viewHolder.bottomText.text = hour
            }
            else -> {
                viewHolder.topText.visibility = GONE
                viewHolder.bottomText.text = fixture.status.short
            }
        }

        AppUtils.bindImage(viewHolder.homeLogo, fixture.home.logo)
        AppUtils.bindImage(viewHolder.awayLogo, fixture.away.logo)

        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(list[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

    fun setList(newList: ArrayList<Fixture>) {
        list = newList
    }

    companion object {
        private const val TAG = "FixtureAdapter"
    }

}
