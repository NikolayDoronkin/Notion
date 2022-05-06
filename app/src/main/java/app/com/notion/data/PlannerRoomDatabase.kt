package app.com.notion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.com.notion.data.converter.DateLongConverter
import app.com.notion.data.dao.EventDao
import app.com.notion.data.dao.NoteDao
import app.com.notion.data.dao.UserDao
import app.com.notion.data.entity.Event
import app.com.notion.data.entity.Note
import app.com.notion.data.entity.User

@Database(entities = [User::class, Note::class, Event::class], version = 1, exportSchema = false)
@TypeConverters(DateLongConverter::class)
abstract class PlannerRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun eventDao(): EventDao

    companion object {

        @Volatile
        private var INSTANCE: PlannerRoomDatabase? = null

        fun getDatabase(context: Context): PlannerRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        PlannerRoomDatabase::class.java,
                        "planner_database"
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
