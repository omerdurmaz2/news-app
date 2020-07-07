package com.example.haber.models

data class TopHeadlinesModel(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
)
