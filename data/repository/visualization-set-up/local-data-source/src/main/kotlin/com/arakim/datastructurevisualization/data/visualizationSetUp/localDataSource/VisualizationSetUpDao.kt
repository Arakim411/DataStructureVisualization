package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisualizationSetUpDao {

    @Insert
    fun insertVisualizationSetUp(visualizationSetUpDto: VisualizationSetUpEntity)

    @Update
    fun updateVisualizationSetUp(visualizationSetUpDto: VisualizationSetUpEntity)

    @Query("SELECT * FROM visualization_set_up WHERE id = :id")
    fun getVisualizationSetUp(id: Int): VisualizationSetUpEntity?

    @Query("SELECT * FROM visualization_set_up WHERE id = :id")
    fun listenForVisualizationSetUpUpdate(id: Int): Flow<VisualizationSetUpEntity?>

}