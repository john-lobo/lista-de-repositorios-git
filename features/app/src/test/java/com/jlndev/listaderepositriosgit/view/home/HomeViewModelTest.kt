package com.jlndev.listaderepositriosgit.view.home

import androidx.lifecycle.Observer
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.githubservice.data.api.model.GithubResponse
import com.jlndev.githubservice.data.service.GithubRepository
import com.jlndev.listaderepositriosgit.BaseViewModelTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test
import java.util.concurrent.TimeUnit

class HomeViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: GithubRepository
    private lateinit var onRepositoryFirstPageObserver: Observer<ResponseState<GithubResponse>>
    private lateinit var onRepositoryPageLoadingObserver: Observer<Boolean>

    private val githubResponse = GithubResponse(emptyList())

    override fun setup() {
        repository = mockk()
        onRepositoryFirstPageObserver = mockk(relaxed = true)
        onRepositoryPageLoadingObserver = mockk(relaxed = true)
        viewModel = instantiateViewModel()
    }

    @Test
    fun testSearchFirstRepositoriesSuccess() {
        // Arrange
        every { repository.searchFirstRepositories() } returns Single.just(githubResponse)

        // Act
        viewModel.loadFirstPageItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchFirstRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Success(githubResponse)) }
    }

    @Test
    fun testSearchRepositoriesError() {
        // Arrange
        val error = Throwable("Erro na busca de repositórios")
        every { repository.searchFirstRepositories() } returns Single.error(error)

        // Act
        viewModel.loadFirstPageItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchFirstRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Error(error)) }
    }

    @Test
    fun testSearchMoreRepositoriesSuccess() {
        // Arrange
        every { repository.searchMoreRepositories() } returns Single.just(githubResponse)

        // Act
        viewModel.loadMoreItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchMoreRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Success(githubResponse)) }
    }

    @Test
    fun testSearchMoreRepositoriesError() {
        // Arrange
        val error = Throwable("Erro na busca de repositórios")
        every { repository.searchMoreRepositories() } returns Single.error(error)

        // Act
        viewModel.loadMoreItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchMoreRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Error(error)) }
    }

    @Test
    fun testClearAndSearchFirstRepositoriesSuccess() {
        // Arrange
        every { repository.clearAndSearchFirstRepositories() } returns Single.just(githubResponse)

        // Act
        viewModel.clearAndSearchFirstRepositories()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.clearAndSearchFirstRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Success(githubResponse)) }
    }

    @Test
    fun estClearAndSearchFirstRepositoriesError() {
        // Arrange
        val error = Throwable("Erro na busca de repositórios")
        every { repository.clearAndSearchFirstRepositories() } returns Single.error(error)

        // Act
        viewModel.clearAndSearchFirstRepositories()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.clearAndSearchFirstRepositories() }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Error(error)) }
    }

    private fun instantiateViewModel(): HomeViewModel {
        val viewModel = HomeViewModel(testScheduler, repository)
        observeLiveData(viewModel.repositoryFirstPageLive, onRepositoryFirstPageObserver)
        observeLiveData(viewModel.repositoryMorePageLive, onRepositoryFirstPageObserver)
        observeLiveData(viewModel.updateRepositoryLive, onRepositoryFirstPageObserver)
        return viewModel
    }

}