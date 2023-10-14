package com.jlndev.listaderepositriosgit.view.home.adapter.model

import android.os.Parcelable
import com.jlndev.coreandroid.bases.adapter.BaseDiffItemView
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitRepositoryItem(
    override val id: String,
    val repositoryName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val avatarUrl: String,
    val ownerName: String,
    val description: String,
    val visibility: String,
    val language: String,
    val viewType: Int = 0,
): Parcelable, BaseDiffItemView() {
    companion object {
        val LOADING = GitRepositoryItem("LOADING", "", 0, 0, "", "", "","","",1)
        val EMPTY  = GitRepositoryItem("EMPTY", "", 0, 0, "", "", "DADOS NÃO ENCONTRADOS","","",0)
    }
}
