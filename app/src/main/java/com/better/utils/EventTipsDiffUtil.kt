package com.better.utils

import androidx.recyclerview.widget.DiffUtil
import com.better.model.dataHolders.EventTip
import com.better.model.dataHolders.Fixture

class EventTipsDiffUtil(
    private val oldList: List<EventTip>,
    private val newList: List<EventTip>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].tipID == newList[newItemPosition].tipID
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
