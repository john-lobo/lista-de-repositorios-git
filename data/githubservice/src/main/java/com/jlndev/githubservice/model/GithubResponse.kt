package com.jlndev.githubservice.model

import java.io.Serializable

data class GithubResponse(
    val items: List<GithubRepositoryResponse>,
) : Serializable