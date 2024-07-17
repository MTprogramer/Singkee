package com.Singlee.forex.di

import android.content.Context
import com.Singlee.forex.Repo.Auth.AuthRepository
import com.Singlee.forex.Repo.Auth.AuthRepositoryImpl
import com.Singlee.forex.Repo.User.UserRepo
import com.Singlee.forex.Repo.User.UserRepoImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
    @Provides
    @Singleton
    fun provideFireAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun authRepoImp(firebaseAuth: FirebaseAuth , firebaseFirestore: FirebaseFirestore , @ApplicationContext context: Context) : AuthRepository
    {
        return AuthRepositoryImpl(firebaseAuth , firebaseFirestore , context  )
    }

    @Provides
    @Singleton
    fun userRepoImp(firebaseAuth: FirebaseAuth , firebaseFirestore: FirebaseFirestore , @ApplicationContext context: Context) : UserRepo
    {
        return UserRepoImpl(firebaseAuth , firebaseFirestore ,context)
    }
}