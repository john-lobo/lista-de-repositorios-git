package com.jlndev.githubservice.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jlndev.githubservice.data.db.model.GithubPageEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdatePage(githubPageEntity: GithubPageEntity) : Completable

    @Query("SELECT * FROM githubPage WHERE id = 1")
    fun getPage(): Single<GithubPageEntity?>
}