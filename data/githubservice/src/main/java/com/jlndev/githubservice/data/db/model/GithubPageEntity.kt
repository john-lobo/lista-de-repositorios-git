package com.jlndev.githubservice.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "githubPage")
data class GithubPageEntity(
    @PrimaryKey
    val id: Int = 1,
    val page: Int?
)