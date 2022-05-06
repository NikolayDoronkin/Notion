package app.com.notion

import android.app.Application
import app.com.notion.data.PlannerRoomDatabase
import app.com.notion.data.repository.EventRepository
import app.com.notion.data.repository.NoteRepository
import app.com.notion.data.repository.UserRepository

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    val database by lazy { PlannerRoomDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }
    val eventRepository by lazy { EventRepository(database.eventDao()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}