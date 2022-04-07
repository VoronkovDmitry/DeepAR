package com.example.camerax_compose.di

import android.content.Context
import com.example.camerax_compose.domain.PermissionRepository
import com.example.camerax_compose.domain.PermissionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideCheckPermissionUseCase(@ApplicationContext context: Context):PermissionRepository{
        return PermissionRepositoryImpl(context = context)
    }
}