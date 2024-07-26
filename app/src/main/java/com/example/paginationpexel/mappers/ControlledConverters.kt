package com.example.paginationpexel.mappers

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.paginationpexel.dto.Src
import com.example.paginationpexel.local.PhotoEntity
import com.example.paginationpexel.local.SrcEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ControlledConverters(
    private val gson: Gson
) {

    @TypeConverter
    fun fromPhotoEntityListToJsonString( photoEntity: List<PhotoEntity> ) : String{
        return try {
            gson.toJson(
                photoEntity,
                object : TypeToken<List<PhotoEntity>>(){}.type
            )
        }catch (e : Exception){
            Log.e("ControlledConverters", e.toString())
            "[]"
        }
    }

    @TypeConverter
    fun fromJsonStringToPhotoEntityList( json : String ) : List<PhotoEntity>{
        return try {
            gson.fromJson(
                json,
                object : TypeToken<List<PhotoEntity>>(){}.type
            )
        }catch (e : Exception){
            Log.e("ControlledConverters", e.toString())
            emptyList()
        }
    }

    @TypeConverter
    fun fromSrcEntityToJsonString( srcEntity: SrcEntity ) : String{
        return try {
            gson.toJson(
                srcEntity
            )
        }catch (e : Exception){
            Log.e("ControlledConverters", e.toString())
            "[]"
        }
    }

    @TypeConverter
    fun fromJsonStringToSrcEntity( json : String ) : SrcEntity{
        return try {
            gson.fromJson(
                json,
                SrcEntity::class.java
            )
        }catch (e : Exception){
            Log.e("ControlledConverters", e.toString())
            SrcEntity("")
        }
    }

}