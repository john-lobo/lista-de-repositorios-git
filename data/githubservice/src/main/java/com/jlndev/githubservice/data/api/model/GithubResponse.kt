package com.jlndev.githubservice.data.api.model

import java.io.Serializable

data class GithubResponse(
    val items: List<GithubRepositoryResponse>,
) : Serializable