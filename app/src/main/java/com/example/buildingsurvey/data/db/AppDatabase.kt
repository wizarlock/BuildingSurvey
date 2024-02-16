package com.example.buildingsurvey.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildingsurvey.data.db.entities.AudioDbEntity
import com.example.buildingsurvey.data.db.entities.DrawingDbEntity
import com.example.buildingsurvey.data.db.entities.LabelDbEntity
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity


@Database(
    version = 1,
    entities = [
        ProjectDbEntity::class,
        DrawingDbEntity::class,
        LabelDbEntity::class,
        AudioDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProjectDao(): ProjectDao

    abstract fun getDrawingDao(): DrawingDao

    abstract fun getAudioDao(): AudioDao

    abstract fun getLabelDao(): LabelDao

    companion object {
        private var database: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase {
            return if (database == null) {
                synchronized(this) {
                    Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
                        .createFromAsset("checkpoint_database.db")
                        .build()
                }
            } else {
                database!!
            }
        }
    }
}