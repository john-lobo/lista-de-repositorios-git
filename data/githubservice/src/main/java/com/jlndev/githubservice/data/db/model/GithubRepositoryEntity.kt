package com.jlndev.githubservice.data.db.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "githubRepository")
data class GithubRepositoryEntity(
    @PrimaryKey val id: Int,
    val repositoryName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val avatarUrl: String,
    val login: String,
    val description: String,
    val visibility: String,
    val language: String
)