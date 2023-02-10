package com.example.a7minutesworkoutapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("select * from `history-table`")
    fun fetchAllDates(): Flow<List<HistoryEntity>>

}