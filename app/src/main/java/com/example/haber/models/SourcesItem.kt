package com.example.haber.models

import com.google.gson.annotations.SerializedName

data class SourcesItem(
    @SerializedName("country")
    val country: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

	@SerializedName("language")
	val language: String? = null,

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("category")
	val category: String? = null,

	@SerializedName("url")
	val url: String? = null
)
