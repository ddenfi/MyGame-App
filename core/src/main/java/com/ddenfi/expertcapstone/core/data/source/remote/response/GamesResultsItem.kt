package com.ddenfi.expertcapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResultsItem(

	@field:SerializedName("added")
	val added: Int,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("metacritic")
	val metacritic: Int,

	@field:SerializedName("playtime")
	val playtime: Int,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItem>,

	@field:SerializedName("background_image")
	val backgroundImage: String,

	@field:SerializedName("tba")
	val tba: Boolean,

	@field:SerializedName("esrb_rating")
	val esrbRating: EsrbRating,

	@field:SerializedName("rating_top")
	val ratingTop: Int,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatus,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int,

	@field:SerializedName("updated")
	val updated: String,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("parent_platforms")
	val parentPlatforms:List<ParentPlatformItem>
)
