package com.ddenfi.expertcapstone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ParentPlatformItem(
    @field:SerializedName("platform")
    val platform: Platform
)
