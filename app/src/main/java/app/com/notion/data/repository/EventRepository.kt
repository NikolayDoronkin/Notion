package app.com.notion.data.repository

import androidx.annotation.WorkerThread
import app.com.notion.data.dao.EventDao
import app.com.notion.data.entity.Event

class EventRepository(private val eventDao: EventDao) {

    @WorkerThread
    suspend fun insert(event: Event) = eventDao.insert(event)

    @WorkerThread
    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    @WorkerThread
    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    @WorkerThread
    suspend fun getEvents(userId: Long) = eventDao.getEvents(userId)

    @WorkerThread
    suspend fun getEvent(id: Long) = eventDao.getEvent(id)
}
