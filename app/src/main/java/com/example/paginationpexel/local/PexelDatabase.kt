package com.example.paginationpexel.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paginationpexel.dto.PexelDto
import com.example.paginationpexel.mappers.ControlledConverters

@Database(
    entities = [PexelEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(ControlledConverters::class)
abstract class PexelDatabase  : RoomDatabase(){

    abstract val  dao : PexelDao

}