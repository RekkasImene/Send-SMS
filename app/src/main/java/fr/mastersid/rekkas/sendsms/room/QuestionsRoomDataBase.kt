package fr.mastersid.rekkas.sendsms.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.mastersid.rekkas.sendsms.adapters.Questions

@Database(
    entities = [Questions ::class],
    version = 2,
    exportSchema = false
)

abstract class QuestionsRoomDataBase : RoomDatabase() {
    abstract fun questionsDao (): QuestionDao
}