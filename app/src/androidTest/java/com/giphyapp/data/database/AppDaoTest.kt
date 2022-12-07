package com.giphyapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.giphyapp.getOrAwaitValue
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: AppDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getAppDao()

    }

    @After
    fun destroy() {
        database.close()
    }

    @Test
    fun insertUser() {
        val user = UserBean(1, "test", "26", "30/05/1998", "", "Dev")
        dao.insert(user)

        val allUsers = dao.allUsers().getOrAwaitValue()

        assertEquals(allUsers,user)
    }

    @Test
    fun deleteUser() {
        val user = UserBean(1, "test", "26", "30/05/1998", "", "Dev")
        dao.insert(user)
        dao.delete(user)

        val allUsers = dao.allUsers().getOrAwaitValue()



        assertNotEquals(allUsers,user)
    }

    @Test
    fun updateUser() {
        val user = UserBean(1, "test", "26", "30/05/1998", "", "Dev")
        dao.insert(user)

        user.birthDate = "27"

        dao.update(user)

        val allUsers = dao.allUsers().getOrAwaitValue()

        assertNotEquals(allUsers,user)
    }

}