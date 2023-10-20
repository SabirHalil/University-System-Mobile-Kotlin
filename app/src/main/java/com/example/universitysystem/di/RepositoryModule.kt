package com.example.universitysystem.di

import com.example.universitysystem.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(): StudentRepository {
        return StudentRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideLectureRepository(): LecturesRepository {
        return LecturesRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideAnnouncementRepository(): AnnouncementRepository {
        return AnnouncementRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepositoryImp()
    }
    @Provides
    @Singleton
    fun provideMealRepository(): MealRepository {
        return MealRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideFaqRepository(): FaqRepository {
        return FaqRepositoryImp()
    }
}