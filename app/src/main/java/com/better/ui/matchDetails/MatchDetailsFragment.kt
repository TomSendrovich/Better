package com.better.ui.matchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.better.R
import com.better.model.dataHolders.Fixture
import com.better.utils.Utils
import kotlinx.android.synthetic.main.fragment_match_details.view.*


class MatchDetailsFragment : Fragment() {

    private val args by navArgs<MatchDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_match_details, container, false)

        Utils.bindImage(view.home_imageView, args.selectedFixture.home.logo)
        Utils.bindImage(view.away_imageView, args.selectedFixture.away.logo)

        view.home_textView.text = args.selectedFixture.home.name
        view.away_textView.text = args.selectedFixture.away.name

        if (args.selectedFixture.status.elapsed == null) {
            view.status_textView.visibility = View.GONE
            view.score_textView.visibility = View.GONE
            view.date_textView.text = Fixture.buildStatusText(args.selectedFixture)
        } else {
            view.status_textView.text = args.selectedFixture.status.elapsed.toString()
            view.date_textView.visibility = View.GONE
        }

        return view
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}
