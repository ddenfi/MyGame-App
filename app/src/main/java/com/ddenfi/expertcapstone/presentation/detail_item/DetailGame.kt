package com.ddenfi.expertcapstone.presentation.detail_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ddenfi.expertcapstone.core.R
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.utils.GAME_ID
import com.ddenfi.expertcapstone.core.utils.Resource
import com.ddenfi.expertcapstone.databinding.ActivityDetailGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailGame : AppCompatActivity() {
    private lateinit var binding: ActivityDetailGameBinding
    private val viewModel: DetailGameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraGameId = intent.getIntExtra(GAME_ID, 0)

        viewModel.getDetailGame(extraGameId).observe(this) { gameDetail ->
            Log.d("Remote UI","called by id")
            if (gameDetail != null) {
                when (gameDetail) {
                    is Resource.Loading -> {
                        binding.pbDetailGame.visibility = View.VISIBLE
                        gameDetail.data?.let { setView(it) }
                    }
                    is Resource.Success -> {
                        binding.pbDetailGame.visibility = View.GONE
                        gameDetail.data?.let { setView(it) }
                    }
                    is Resource.Error -> {
                        binding.pbDetailGame.visibility = View.VISIBLE
                        Toast.makeText(this@DetailGame, gameDetail.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setFab(isFavorite: Boolean?) {
        if (isFavorite == true) {
            binding.fabDetailGame.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.fabDetailGame.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setView(gameDetail: GameDetail) {
        binding.apply {
            setFab(gameDetail.isFavorite)
            supportActionBar?.title = gameDetail.name ?: ""
            tvDetailGameName.text = gameDetail.name ?: ""
            tvDetailGameDesc.text = Html.fromHtml(gameDetail.description ?: "")
            tvDetailGameErsb.text = gameDetail.esrbRating ?: ""
            tvDetailGamePlay.text = gameDetail.playTime.toString()
            Glide.with(this@DetailGame)
                .load(gameDetail.backgroundImage)
                .into(ivGameImage)
        }
        binding.fabDetailGame.setOnClickListener {
            gameDetail.isFavorite?.let { isFavorite ->
                viewModel.setFavoriteGame(
                    gameDetail.id,
                    !isFavorite
                )
            }
        }
    }
}