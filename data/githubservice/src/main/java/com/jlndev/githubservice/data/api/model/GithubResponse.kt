package com.jlndev.githubservice.data.api.model

import java.io.Serializable

class GithubResponse(
    val items: List<GithubRepositoryResponse>,
) : Serializable