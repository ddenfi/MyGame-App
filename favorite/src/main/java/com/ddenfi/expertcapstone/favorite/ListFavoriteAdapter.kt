package com.ddenfi.expertcapstone.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddenfi.expertcapstone.core.databinding.ItemGameBinding
import com.ddenfi.expertcapstone.core.domain.model.GameDetail


class ListFavoriteAdapter : RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listDetailGame: ArrayList<GameDetail> = ArrayList()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<GameDetail>) {
        listDetailGame.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameDetail) {
            binding.apply {
                tvGameName.text = item.name
                tvGameReleased.text = item.released
                tvRating.text = item.rating
            }

            for (i in item.parentPlatforms?.indices!!) {
                when (item.parentPlatforms?.get(i)) {
                    1 -> binding.ivGamePc.visibility = View.VISIBLE
                    2 -> binding.ivGamePs.visibility = View.VISIBLE
                    3 -> binding.ivGameXbox.visibility = View.VISIBLE
                    4 -> binding.ivGameIos.visibility = View.VISIBLE
                    5 -> binding.ivGameAndroid.visibility = View.VISIBLE
                    7 -> binding.ivGameNintendo.visibility = View.VISIBLE
                    else -> binding.ivGameDefault.visibility = View.VISIBLE
                }
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
        val detailGame = listDetailGame[position]
        holder.bind(detailGame)
    }

    override fun getItemCount(): Int = listDetailGame.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GameDetail)
    }
}