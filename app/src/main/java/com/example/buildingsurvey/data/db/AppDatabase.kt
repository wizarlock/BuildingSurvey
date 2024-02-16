package com.example.buildingsurvey.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildingsurvey.data.db.entities.ProjectDbEntity


@Database(
    version = 1,
    entities = [
        ProjectDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProjectDao(): ProjectDao

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