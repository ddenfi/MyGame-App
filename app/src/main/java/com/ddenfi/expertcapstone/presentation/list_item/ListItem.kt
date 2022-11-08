package com.ddenfi.expertcapstone.presentation.list_item

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddenfi.expertcapstone.R
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.ui.ListItemAdapter
import com.ddenfi.expertcapstone.presentation.detail_item.DetailGame
import com.ddenfi.expertcapstone.core.utils.GAME_ID
import com.ddenfi.expertcapstone.core.utils.IS_FAV
import com.ddenfi.expertcapstone.core.utils.Resource
import com.ddenfi.expertcapstone.databinding.ActivityListItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItem : AppCompatActivity() {
    private lateinit var binding: ActivityListItemBinding
    private val viewModel: ListItemViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "List Games"

        val rvAdapter = ListItemAdapter()

        setView(rvAdapter)
        setRecyclerView(rvAdapter)
    }

    private fun setView(rvAdapter: ListItemAdapter) {
        viewModel.allGame.observe(this) {
            when (it) {
                is Resource.Loading -> binding.pgGame.visibility = View.VISIBLE
                is Resource.Success -> it.data?.let { it1 ->
                    rvAdapter.setData(it1)
                    binding.pgGame.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.pgGame.visibility = View.GONE
                    Toast.makeText(this@ListItem, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val uri = Uri.parse("favoriteapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(rvAdapter: ListItemAdapter) {
        binding.rvGame.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ListItem)
            adapter = rvAdapter
        }

        rvAdapter.setOnItemClickCallback(object : ListItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Game) {
                Log.d("GAME ID LIST", data.id.toString())
                val intentToDetail = Intent(this@ListItem, DetailGame::class.java)
                intentToDetail.apply {
                    putExtra(GAME_ID, data.id)
                    putExtra(IS_FAV, data.isFavorite)
                }
                startActivity(intentToDetail)
            }
        })
    }
}