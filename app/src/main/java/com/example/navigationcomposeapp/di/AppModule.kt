package com.example.navigationcomposeapp.di

import com.example.navigationcomposeapp.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return RemoteDataSource.ApiInterface.BASE_URL
    }

    @Singleton
    @Provides
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(baseUrl: String, okkhttp : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okkhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): RemoteDataSource.ApiInterface {
        return retrofit.create(RemoteDataSource.ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteSource(apiInterface: RemoteDataSource.ApiInterface): RemoteDataSource =
        RemoteDataSource(apiInterface)
}