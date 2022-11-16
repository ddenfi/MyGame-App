package com.ddenfi.expertcapstone.presentation.list_item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddenfi.expertcapstone.R
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.ui.ListItemAdapter
import com.ddenfi.expertcapstone.core.ui.LoadingStateAdapter
import com.ddenfi.expertcapstone.core.utils.GAME_ID
import com.ddenfi.expertcapstone.core.utils.IS_FAV
import com.ddenfi.expertcapstone.databinding.ActivityListItemBinding
import com.ddenfi.expertcapstone.presentation.detail_item.DetailGame
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        setRecyclerView(rvAdapter)
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
            adapter = rvAdapter.withLoadStateFooter(footer = LoadingStateAdapter{
                rvAdapter.retry()
            })
            lifecycleScope.launch {
                viewModel.allGame.collectLatest { pagingData: PagingData<Game> ->
                    rvAdapter.submitData(pagingData)
                }
            }
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