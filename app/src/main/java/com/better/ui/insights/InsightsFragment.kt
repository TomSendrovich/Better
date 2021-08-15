package com.better.ui.insights

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.better.R
import com.better.model.dataHolders.EventTip
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InsightsFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: InsightsViewModel
    private val args by navArgs<InsightsFragmentArgs>()
    private lateinit var anyChartView: AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InsightsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insights, container, false)

        anyChartView = view.findViewById(R.id.any_chart_view_insight)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tip: EventTip = args.selectedEventTip

        viewModel.updateEventTipsByUserId(tip.userID)
        viewModel.eventTips.observe(viewLifecycleOwner, {
            viewModel.getTeamsPercent()
        })
        viewModel.pie.observe(viewLifecycleOwner, { map ->
            setupChart(map)
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setupChart(map: HashMap<String, Double>?) {
        val chart: Cartesian = AnyChart.column()
        val dataEntries: ArrayList<DataEntry> = arrayListOf()

        dataEntries.add(ValueDataEntry(args.selectedEventTip.homeName,
            map!![args.selectedEventTip.homeName]))
        dataEntries.add(ValueDataEntry(args.selectedEventTip.awayName,
            map[args.selectedEventTip.awayName]))

        val column: Column = chart.column(dataEntries)

        column
            .tooltip()
            .titleFormat("{%X}") // title of tooltip
            .format("{%Value}%") // text format

        chart
            .title("Hit Rate By Team")
            .background("#000000")
            .animation(true)
            .data(dataEntries)

        chart.xAxis(0).title("Team");
        chart.yAxis(0).title("Hit Rate");

        anyChartView.setChart(chart)
    }
}
