package com.better.ui.addTip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.better.R
import com.better.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add_tip.*

class AddTipFragment : BottomSheetDialogFragment() {
    private lateinit var addTipButton: Button
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_tip, container, false)
        addTipButton = view.findViewById(R.id.add_tip_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTipButton.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val chipId = chipGroup.checkedChipId
            if (chipId != -1) {
                val chip: Chip = chipGroup.findViewById(chipId)
                var tipValue: Long = 0
                val tipValueStr = chip.text.toString().apply {
                    when {
                        equals("1") -> {
                            tipValue = 0
                        }
                        equals("X") -> {
                            tipValue = 1
                        }
                        equals("2") -> {
                            tipValue = 2
                        }
                    }
                    viewModel.createEventTipDocument(args.selectedFixture, description, tipValue)
                    viewModel.updateEventTipsByFixtureId(args.selectedFixture.id)
                    dismiss()
                }
            } else {
                Toast.makeText(context, "please place your bet choice", Toast.LENGTH_LONG).show()
            }

        }
    }


}
