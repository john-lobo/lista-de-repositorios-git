package com.jlndev.listaderepositriosgit.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlndev.baseservice.ext.BaseSchedulerProvider
import com.jlndev.baseservice.ext.disposedBy
import com.jlndev.baseservice.ext.processSingle
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.githubservice.data.GithubRepository
import com.jlndev.githubservice.model.GitHubSearchResponse
import com.jlndev.listaderepositriosgit.bases.BaseViewModel

class HomeViewModel(
    private val schedulerProvider: BaseSchedulerProvider,
    private val repository: GithubRepository
) : BaseViewModel() {

    private val _repositoryGitLive = MutableLiveData<ResponseState<GitHubSearchResponse>>()
    val repositoryGitLive : LiveData<ResponseState<GitHubSearchResponse>>
        get() = _repositoryGitLive

    internal fun searchRepositories() {
        repository.searchRepositories(1)
            .processSingle(schedulerProvider)
            .doOnSubscribe { _repositoryGitLive.value = ResponseState.Loading(true) }
            .doOnSuccess {
                _repositoryGitLive.value = ResponseState.Loading(false)
                _repositoryGitLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryGitLive.value = ResponseState.Loading(false)
                _repositoryGitLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }
}