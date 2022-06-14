package com.softserve.teachua.app

import android.content.Context
import com.softserve.teachua.data.retrofit.RetrofitApi
import com.softserve.teachua.data.retrofit.RetrofitClient
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.data.sharedpreferences.CurrentUserSharedPreferencesInterface
import com.softserve.teachua.data.sharedpreferences.SharedPreferences
import com.softserve.teachua.domain.*
import com.softserve.teachua.domain.interfaces.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule () {


    //
    //Domain
    //
    @Singleton
    @Provides
    fun providesCitiesUseCasesInterface() : CitiesUseCasesInterface{
        return CitiesUseCases(providesRemoteDataSource())
    }


    @Singleton
    @Provides
    fun providesCurrentUserUseCasesInterface(@ApplicationContext appContext: Context) : CurrentUserUseCasesInterface{
        return CurrentUserUseCases(providesCurrentUserSharedPreferencesInterface(appContext))
    }


    @Singleton
    @Provides
    fun providesStationsUseCasesInterface() : StationsUseCasesInterface{
        return StationsUseCases(providesRemoteDataSource())
    }


    @Singleton
    @Provides
    fun providesDistrictUseCasesInterface():DistrictUseCasesInterface{
        return DistrictUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun provideCategoriesUseCases() : CategoriesUseCasesInterface {
        return CategoriesUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun provideUserUseCases() : UserUseCasesInterface {
        return UserUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun provideBannersUseCases() : BannerUseCasesInterface {
        return BannerUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesChallengeUseCasesUseCases(): ChallengesUseCasesInterface {
        return ChallengeUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesNewsUseCases(): NewsUseCasesInterface {
        return NewsUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesAboutUseCases(): AboutUseCasesInterface {
        return AboutUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesTaskUseCases():TaskUseCasesInterface{
        return TaskUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesFeedbackUseCases():FeedbacksUseCasesInterface{
        return FeedbacksUseCases(providesRemoteDataSource())
    }

    @Singleton
    @Provides
    fun providesMessageUseCases():MessagesUseCasesInterface{
        return MessageUseCases(providesRemoteDataSource())
    }


    //
    //Remote Data Source (RETROFIT, RemoteDataSource)
    //
    @Singleton
    @Provides
    fun providesRemoteDataSource():RemoteDataSource{
        return RemoteDataSource(providesRetrofitService())
    }

    @Singleton
    @Provides
    fun providesRetrofitService(): RetrofitApi {
        return RetrofitClient().getClient()
    }



    @Singleton
    @Provides
    fun providesCurrentUserSharedPreferencesInterface( @ApplicationContext appContext: Context ):CurrentUserSharedPreferencesInterface{
        return SharedPreferences(appContext)
    }
}