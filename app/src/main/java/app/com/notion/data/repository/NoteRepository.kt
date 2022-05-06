package app.com.notion.data.repository

import androidx.annotation.WorkerThread
import app.com.notion.data.dao.NoteDao
import app.com.notion.data.entity.Note

class NoteRepository(private val noteDao: NoteDao) {

    @WorkerThread
    suspend fun insert(note: Note) = noteDao.insert(note)

    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    @WorkerThread
    suspend fun getNotes(userId: Long) = noteDao.getNotes(userId)

    @WorkerThread
    suspend fun getNote(id: Long) = noteDao.getNote(id)
}
