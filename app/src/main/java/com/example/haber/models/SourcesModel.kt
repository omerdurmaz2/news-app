package com.example.haber.models

import com.google.gson.annotations.SerializedName

data class SourcesModel(
	@SerializedName("sources")
	val sources: List<SourcesItem?>? = null,

	@SerializedName("status")
	val status: String? = null
)
