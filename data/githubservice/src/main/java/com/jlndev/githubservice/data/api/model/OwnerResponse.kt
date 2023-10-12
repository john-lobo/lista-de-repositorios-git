package com.jlndev.githubservice.data.api.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OwnerResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val login: String?
) : Serializable