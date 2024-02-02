package com.arakim.datastructurevisualization.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.arakim.datastructurevisaulization.data.database.AppDatabase
import com.arakim.datastructurevisualization.kotlinutil.getOrThrow
import com.arakim.datastructurevisualization.testdatagenerator.randomString
import com.arakim.datastructurevisualization.testutil.CoroutineTest
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureDao
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureLocalDataSource
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DataStructureLocalDataSourceTest : CoroutineTest() {

    lateinit var subject: DataStructureLocalDataSource

    private lateinit var db: AppDatabase
    private lateinit var dao: DataStructureDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.dataStructureDao()
        subject = DataStructureLocalDataSource(dao)
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun data_structure_is_correctly_created() = runTest {
        val name = randomString()
        val type = DataStructureType.values().random()

        val id = subject.createDataStructure(name, type).getOrThrow()
        val dataStructure = dao.getDataStructure(id)!!

        assertThat(dataStructure.id).isEqualTo(id)
        assertThat(dataStructure.name).isEqualTo(name)
    }


    @Test
    fun data_structure_is_correctly_updated() = runTest {
        val name = randomString()
        val type = DataStructureType.values().random()

        val id = subject.createDataStructure(name, type).getOrThrow()
        val dataStructure = subject.getDataStructure(id).getOrThrow()

        val newName = randomString()

        subject.updateDataStructure(dataStructure.copy(name = newName))
        val updatedDataStructure = dao.getDataStructure(id)!!

        assertThat(updatedDataStructure.id).isEqualTo(id)
        assertThat(updatedDataStructure.name).isEqualTo(newName)
    }

    @Test
    fun data_structure_is_correctly_deleted() = runTest {
        val name = randomString()
        val type = DataStructureType.values().random()

        val id = subject.createDataStructure(name, type).getOrThrow()
        dao.getDataStructure(id)!!
        subject.deleteDataStructure(id)

        assertThat(dao.getDataStructure(id)).isNull()
    }


}