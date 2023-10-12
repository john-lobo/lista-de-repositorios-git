package com.jlndev.githubservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jlndev.githubservice.data.db.model.GithubRepositoryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<GithubRepositoryEntity>) : Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRepositories(repositories: List<GithubRepositoryEntity>) : Completable

    @Query("SELECT * FROM githubRepository")
    fun getAllRepositories(): Single<List<GithubRepositoryEntity>>

    @Query("DELETE FROM githubRepository")
    fun deleteAllRepositories() : Completable
}