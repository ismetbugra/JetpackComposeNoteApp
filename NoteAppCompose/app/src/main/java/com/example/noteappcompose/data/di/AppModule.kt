package com.example.noteappcompose.data.di

import android.content.Context
import androidx.room.Room
import com.example.noteappcompose.data.datasources.NoteDataSource
import com.example.noteappcompose.data.repositories.NoteRepository
import com.example.noteappcompose.data.room.NoteDatabase
import com.example.noteappcompose.data.room.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(ndso:NoteDataSource):NoteRepository{
        return NoteRepository(ndso)
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(ndao:NotesDao):NoteDataSource{
        return NoteDataSource(ndao)
    }

    @Provides
    @Singleton
    fun provideNotesDao(@ApplicationContext context: Context):NotesDao{
        return Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,
            "notes_database.sqlite").build().getNoteDao()
    }
}