package com.example.buildingsurvey.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildingsurvey.data.db.dao.AudioDao
import com.example.buildingsurvey.data.db.dao.DefectDao
import com.example.buildingsurvey.data.db.dao.DefectPointDao
import com.example.buildingsurvey.data.db.dao.DrawingDao
import com.example.buildingsurvey.data.db.dao.LabelDao
import com.example.buildingsurvey.data.db.dao.ProjectDao
import com.example.buildingsurvey.data.db.dao.TextDao
import com.example.buildingsurvey.data.db.dao.TypeOfDefectDao
import com.example.buildingsurvey.data.db.entities.AudioDbEntity
import com.example.buildingsurvey.data.db.entities.DefectDbEntity
import com.example.buildingsurvey.data.db.entities.DefectPointDbEntity
import com.example.buildingsurvey.data.db.entities.DrawingDbEntity
import com.example.buildingsurvey.data.db.entities.LabelDbEntity
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity
import com.example.buildingsurvey.data.db.entities.TextDbEntity
import com.example.buildingsurvey.data.db.entities.TypeOfDefectDbEntity


@Database(
    version = 1,
    entities = [
        ProjectDbEntity::class,
        DrawingDbEntity::class,
        LabelDbEntity::class,
        AudioDbEntity::class,
        TypeOfDefectDbEntity::class,
        DefectDbEntity::class,
        DefectPointDbEntity::class,
        TextDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProjectDao(): ProjectDao

    abstract fun getDrawingDao(): DrawingDao

    abstract fun getAudioDao(): AudioDao

    abstract fun getLabelDao(): LabelDao

    abstract fun getTypeOfDefectDao(): TypeOfDefectDao

    abstract fun getDefectDao(): DefectDao

    abstract fun getDefectPointDao(): DefectPointDao

    abstract fun getTextDao(): TextDao


    companion object {
        private var database: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            return if (database == null) {
                synchronized(this) {
                    Room.databaseBuilder(context, AppDatabase::class.java, "checkpoint_database.db")
                        .createFromAsset("checkpoint_database.db")
                        .build()
                }
            } else {
                database!!
            }
        }
    }
}