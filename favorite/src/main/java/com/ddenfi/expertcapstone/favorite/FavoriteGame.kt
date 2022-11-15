package com.ddenfi.expertcapstone.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.ui.ListItemAdapter
import com.ddenfi.expertcapstone.core.utils.GAME_ID
import com.ddenfi.expertcapstone.core.utils.IS_FAV
import com.ddenfi.expertcapstone.core.utils.Resource
import com.ddenfi.expertcapstone.core.utils.UserComparator
import com.ddenfi.expertcapstone.di.FavoriteModuleDependencies
import com.ddenfi.expertcapstone.favorite.databinding.ActivityFavoriteGameBinding
import com.ddenfi.expertcapstone.presentation.detail_item.DetailGame
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteGame : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteGameBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteGameViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Favorite Game"
        binding = ActivityFavoriteGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvAdapter = ListItemAdapter()
        setRecyclerView(rvAdapter)
//        setView(rvAdapter)
    }

//    private fun setView(rvAdapter: ListItemAdapter) {
//        viewModel.result.observe(this) {
//            when (it) {
//                is Resource.Loading -> binding.pgGameFav.visibility = View.VISIBLE
//                is Resource.Success -> it.data?.let { gameDetail ->
//                    rvAdapter
//                    binding.pgGameFav.visibility = View.GONE
//                }
//                is Resource.Error -> {
//                    binding.pgGameFav.visibility = View.GONE
//                    Toast.makeText(this@FavoriteGame, it.message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//    }

    private fun setRecyclerView(rvAdapter: ListItemAdapter) {
        binding.rvGameFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteGame)
            adapter = rvAdapter
        }
        rvAdapter.setOnItemClickCallback(object : ListItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Game) {
                Log.d("GAME ID LIST", data.id.toString())
                val intentToDetail = Intent(this@FavoriteGame, DetailGame::class.java)
                intentToDetail.apply {
                    putExtra(GAME_ID, data.id)
                    putExtra(IS_FAV, data.isFavorite)
                }
                startActivity(intentToDetail)
            }
        })
    }
}