package h4rar.space.simpletodo.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import h4rar.space.simpletodo.database.room.dao.NoteRoomDao
import h4rar.space.simpletodo.database.room.dao.TabRoomDao
import h4rar.space.simpletodo.model.Note
import h4rar.space.simpletodo.model.Tab
import h4rar.space.simpletodo.utils.Constants.Keys.NOTE_DATABASE
import java.util.concurrent.Executors

@Database(entities = [Tab::class, Note::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): TabRoomDao
    abstract fun getNoteRoomDao(): NoteRoomDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
                INSTANCE as AppRoomDatabase
            } else {
                return INSTANCE as AppRoomDatabase
            }
        }

        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppRoomDatabase::class.java, NOTE_DATABASE
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            getInstance(context).getRoomDao().addTabFirstTab("Home")
                        }
                    }
                })
                .build()
    }
}