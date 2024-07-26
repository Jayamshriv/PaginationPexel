package com.example.paginationpexel.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.paginationpexel.dto.PexelDto

@Dao
interface PexelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPexel(pexel: PexelEntity): Long

    @Query("SELECT * FROM pexelTable")
    fun pagingSource(): PagingSource<Int, PexelEntity>

    @Query("DELETE FROM pexelTable")
    suspend fun deleteAll()

    //--------------------remote key------------//

    @Query("SELECT * FROM remoteKeyTable WHERE id = :id")
    fun getRemoteKeys(id: Int): RemoteKeysEntity

    @Upsert
    suspend fun insertAllRemoteKeys(remoteKeysEntity: List<RemoteKeysEntity>)

    @Query("DELETE FROM remoteKeyTable")
    suspend fun deleteAllRemoteKeys()

}
