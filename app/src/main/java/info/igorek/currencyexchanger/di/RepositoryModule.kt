package info.igorek.currencyexchanger.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.igorek.currencyexchanger.repository.ApiRepository
import info.igorek.currencyexchanger.repository.ApiRepositoryImpl
import info.igorek.currencyexchanger.repository.DatabaseRepository
import info.igorek.currencyexchanger.repository.DatabaseRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindApiRepository(apiRepository: ApiRepositoryImpl): ApiRepository

    @Binds
    @Singleton
    fun bindDatabaseRepository(databaseRepository: DatabaseRepositoryImpl): DatabaseRepository
}
