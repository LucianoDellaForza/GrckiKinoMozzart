package com.luka.grcki_kino_mozzart.di.modules

import com.luka.grcki_kino_mozzart.data.repo.DrawRepo
import com.luka.grcki_kino_mozzart.data.repo.DrawRepoImpl
import com.luka.grcki_kino_mozzart.di.IODispatcher
import com.luka.grcki_kino_mozzart.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDrawRepo(
        apiService: ApiService,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): DrawRepo {
        return DrawRepoImpl(
            apiService = apiService,
            ioDispatcher = ioDispatcher
        )
    }
}