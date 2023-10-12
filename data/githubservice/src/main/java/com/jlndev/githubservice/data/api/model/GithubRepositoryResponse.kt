package com.jlndev.githubservice.data.api.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GithubRepositoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val repositoryName: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int?,
    @SerializedName("forks_count")
    val forksCount: Int?,
    val owner: OwnerResponse?,
    val description: String?,
    val visibility: String?,
    val language: String?
) : Serializable