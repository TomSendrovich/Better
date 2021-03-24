package com.better.ui.addTip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.better.R
import com.better.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add_tip.*

class AddTipFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddTipViewModel
    private val args by navArgs<AddTipFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddTipViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_tip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_win_chip.text = args.selectedFixture.home.name
        away_win_chip.text = args.selectedFixture.away.name

        addTipButton.setOnClickListener {
            val description = textInputLayoutEditText.text.toString()
            val chipId = chipGroup.checkedChipId
            if (chipId != -1) {
                val chip: Chip = chipGroup.findViewById(chipId)
                viewModel.onClickAddTip(args.selectedFixture, description, chip.text.toString())
                dismiss()
            } else {
                Toast.makeText(context, "please select your match result", Toast.LENGTH_LONG).show()
            }
        }
    }


}
