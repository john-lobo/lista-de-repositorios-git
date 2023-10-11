package com.jlndev.githubservice.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitRepositoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val repositoryName: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    val owner: Owner,
) : Serializable