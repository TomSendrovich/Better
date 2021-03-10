package com.better.ui.matchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.better.R
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_match_details, container, false)

        view.textView.text = "${args.selectedFixture.home.name} - ${args.selectedFixture.away.name}"

        return view
    }

    companion object {
        private const val TAG = "MatchDetailsFragment"
    }
}
