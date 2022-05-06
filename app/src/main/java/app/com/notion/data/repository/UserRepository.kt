package app.com.notion.data.repository

import androidx.annotation.WorkerThread
import app.com.notion.data.dao.UserDao
import app.com.notion.data.entity.User

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insert(user: User) = userDao.insert(user)

    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
    }

    @WorkerThread
    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    @WorkerThread
    suspend fun existUser(name: String, email: String) = userDao.existUser(name, email)

    @WorkerThread
    suspend fun getUser(name: String, password: String) = userDao.getUser(name, password)

    @WorkerThread
    suspend fun getUser(id: Long) = userDao.getUser(id)
}
