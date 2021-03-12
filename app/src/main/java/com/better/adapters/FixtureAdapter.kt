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
import com.better.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


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
        val homeName: TextView = view.findViewById(R.id.home_team_name)
        val awayName: TextView = view.findViewById(R.id.away_team_name)
        val homeLogo: ImageView = view.findViewById(R.id.home_team_logo)
        val awayLogo: ImageView = view.findViewById(R.id.away_team_logo)
        val status: TextView = view.findViewById(R.id.status)
        val score: TextView = view.findViewById(R.id.score)
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
        viewHolder.status.text = Fixture.buildStatusText(fixture)

        val scoreText = Fixture.buildScoreText(fixture)
        if (scoreText == "null-null") {
            viewHolder.score.visibility = GONE
        } else {
            viewHolder.score.text = scoreText
        }

        Utils.bindImage(viewHolder.homeLogo, fixture.home.logo)
        Utils.bindImage(viewHolder.awayLogo, fixture.away.logo)

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
        private const val TAG = "com.better.adapters.FixtureAdapter"
    }

}
