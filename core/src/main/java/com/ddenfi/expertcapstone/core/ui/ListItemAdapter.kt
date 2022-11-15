package com.ddenfi.expertcapstone.core.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ddenfi.expertcapstone.core.databinding.ItemGameBinding
import com.ddenfi.expertcapstone.core.domain.model.Game

class ListItemAdapter :
    PagingDataAdapter<Game, ListItemAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private var binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Game) {
            binding.apply {
                tvGameName.text = item.name
                tvGameReleased.text = item.released
                tvRating.text = item.rating
            }

            for (i in item.parentPlatforms.indices) {
                when (item.parentPlatforms[i]) {
                    1 -> binding.ivGamePc.visibility = View.VISIBLE
                    2 -> binding.ivGamePs.visibility = View.VISIBLE
                    3 -> binding.ivGameXbox.visibility = View.VISIBLE
                    4 -> binding.ivGameIos.visibility = View.VISIBLE
                    5 -> binding.ivGameAndroid.visibility = View.VISIBLE
                    7 -> binding.ivGameNintendo.visibility = View.VISIBLE
                    else -> binding.ivGameDefault.visibility = View.VISIBLE
                }
                Log.d("Item Platfrom", item.parentPlatforms[i].toString())
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Game)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Game,
                newItem: Game
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}