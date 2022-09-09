package net.trexis.shafikexcersie.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.trexis.shafikexcersie.session.Session
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiSession {
    @Provides
    @Singleton
    fun provideSession(@ApplicationContext applicationContext: Context): Session {
        return Session(context = applicationContext)
    }
}