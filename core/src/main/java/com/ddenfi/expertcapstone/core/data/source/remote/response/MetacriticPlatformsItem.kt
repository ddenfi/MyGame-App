package com.ddenfi.expertcapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MetacriticPlatformsItem(

	@field:SerializedName("metascore")
	val metascore: Int,

	@field:SerializedName("url")
	val url: String
)