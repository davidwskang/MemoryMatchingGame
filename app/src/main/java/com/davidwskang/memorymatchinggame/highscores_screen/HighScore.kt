package com.davidwskang.memorymatchinggame.highscores_screen

import android.content.Context
import android.os.Parcelable
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class HighScore(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "initials")
    val initials: String,

    @ColumnInfo(name = "score")
    val score: Int

) : Parcelable

@Database(entities = [HighScore::class], version = 1, exportSchema = false)
abstract class HighScoresDatabase : RoomDatabase() {

    companion object {
        private var instance: HighScoresDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HighScoresDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    HighScoresDatabase::class.java,
                    "high-scores"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun highScoresDao(): HighScoresDao
}

@Dao
interface HighScoresDao {

    @Insert
    fun insert(highScore: HighScore): Completable

    @Query("SELECT * FROM highscore ORDER BY score DESC")
    fun getAll(): Single<List<HighScore>>

}