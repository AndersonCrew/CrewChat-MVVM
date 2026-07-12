package com.crewcloud.apps.crewchat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.crewcloud.apps.crewchat.data.local.entity.ChattingEntity

/**
 * Created by BM Anderson on 12/7/26.
 */
@Dao
interface ChattingDao {
    @Query("SELECT * FROM chattings WHERE roomNo = :roomNo ORDER BY messageNo ASC")
    fun pagingMessage(roomNo: Int): PagingSource<Int, ChattingEntity>

    @Upsert
    suspend fun upSertAll(messages: List<ChattingEntity>)

    @Query("SELECT * FROM chattings WHERE roomNo = :roomNo LIMIT 1")
    suspend fun firstMessage(roomNo: Int): ChattingEntity?

    @Query("SELECT * FROM chattings WHERE roomNo = :roomNo LIMIT 1")
    suspend fun lastMessage(roomNo: Int): ChattingEntity?

}
