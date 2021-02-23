package com.better.ui.matches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.better.R
import com.better.adapters.FixtureAdapter
import com.better.model.dataHolders.Fixture

class MatchesFragment : Fragment() {

    private lateinit var viewModel: MatchesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView

    companion object {
        private const val TAG = "MatchesFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MatchesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = FixtureAdapter(ArrayList())
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.fixtures.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Number of fixtures: " + it.size, Toast.LENGTH_SHORT).show()

            (recyclerView.adapter as FixtureAdapter).setList(it as ArrayList<Fixture>)
            (recyclerView.adapter as FixtureAdapter).notifyDataSetChanged()
        })
    }
}
