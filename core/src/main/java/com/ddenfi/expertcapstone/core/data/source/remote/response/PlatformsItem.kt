package com.ddenfi.expertcapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PlatformsItem(

	@field:SerializedName("requirements")
	val requirements: Requirements,

	@field:SerializedName("released_at")
	val releasedAt: String,

	@field:SerializedName("platform")
	val platform: Platform
)