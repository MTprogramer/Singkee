package com.Singlee.forex.di

import android.content.Context
import android.content.SharedPreferences
import com.Singlee.forex.Repo.Auth.AuthRepository
import com.Singlee.forex.Repo.Auth.AuthRepositoryImpl
import com.Singlee.forex.Repo.User.UserRepo
import com.Singlee.forex.Repo.User.UserRepoImpl
import com.Singlee.forex.Utils.SharedPrefs
import com.facebook.login.LoginManager
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
    fun loginManager() = LoginManager.getInstance()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MY_Pref", Context.MODE_PRIVATE)
    }
    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context , sharedPreferences: SharedPreferences): SharedPrefs {
        return SharedPrefs(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun authRepoImp(firebaseAuth: FirebaseAuth , firebaseFirestore: FirebaseFirestore  , @ApplicationContext context: Context , sharedPrefs: SharedPrefs) : AuthRepository
    {
        return AuthRepositoryImpl(firebaseAuth , firebaseFirestore ,  context , sharedPrefs)
    }

    @Provides
    @Singleton
    fun userRepoImp(firebaseAuth: FirebaseAuth , firebaseFirestore: FirebaseFirestore , @ApplicationContext context: Context) : UserRepo
    {
        return UserRepoImpl(firebaseAuth , firebaseFirestore ,context)
    }
}