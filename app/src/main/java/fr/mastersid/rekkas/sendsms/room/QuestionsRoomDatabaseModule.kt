package fr.mastersid.rekkas.sendsms.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent :: class)
@Module
object QuestionsRoomDatabaseMudule {
    @Provides
    @Singleton
    fun provideQuestionDao(questionRoomDatabase: QuestionsRoomDataBase): QuestionDao {
        return questionRoomDatabase.questionsDao()
    }
    @Provides
    @Singleton
    fun provideQuestionRoomDatabase(@ApplicationContext appContext: Context):
            QuestionsRoomDataBase {
        return Room.databaseBuilder(
            appContext.applicationContext ,
            QuestionsRoomDataBase ::class.java , "question_database"
        ).build()

    }

}