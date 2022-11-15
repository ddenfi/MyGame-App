package com.ddenfi.expertcapstone.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.ddenfi.expertcapstone.core.domain.model.Game

object UserComparator : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}