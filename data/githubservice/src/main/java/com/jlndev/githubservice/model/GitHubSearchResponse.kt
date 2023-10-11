package com.jlndev.githubservice.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitHubSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<GitRepositoryResponse>,
    @SerializedName("total_count")
    val totalCount: Int
) : Serializable