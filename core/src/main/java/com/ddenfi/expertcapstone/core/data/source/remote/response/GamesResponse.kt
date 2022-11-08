package com.ddenfi.expertcapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(

	@field:SerializedName("next")
	val next: String,

	@field:SerializedName("previous")
	val previous: String,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("results")
	val results: List<GamesResultsItem>
)