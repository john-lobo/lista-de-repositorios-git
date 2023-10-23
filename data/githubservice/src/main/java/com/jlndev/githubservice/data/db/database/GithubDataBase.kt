package com.jlndev.githubservice.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jlndev.githubservice.BuildConfig
import com.jlndev.githubservice.data.db.dao.GithubPageDao
import com.jlndev.githubservice.data.db.dao.GithubRepositoryDao
import com.jlndev.githubservice.data.db.model.GithubPageEntity
import com.jlndev.githubservice.data.db.model.GithubRepositoryEntity

@Database(entities = [GithubRepositoryEntity::class, GithubPageEntity::class], version = 1, exportSchema = false)
abstract class GithubDataBase: RoomDatabase() {

    abstract val githubRepositoryDao: GithubRepositoryDao
    abstract val githubPageDao: GithubPageDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDataBase? = null

        fun getInstance(context: Context): GithubDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDataBase::class.java,
                    BuildConfig.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}