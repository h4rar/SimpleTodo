package h4rar.space.simpletodo.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import h4rar.space.simpletodo.model.Tab

@Dao
interface TabRoomDao {

    @Query("SELECT * FROM tabs_table")
    fun getAllTabs(): LiveData<List<Tab>>

    @Query("SELECT * FROM tabs_table ORDER BY onum")
    fun readAllSort(): LiveData<List<Tab>>

    @Query("DELETE FROM tabs_table WHERE id = :tabId")
    fun deleteTab(tabId: Int)

    @Query(
        "Insert into tabs_table (name, onum) select :name, t.onum + 1 from tabs_table t order by onum DESC limit 1"
    )
    fun addTab(name: String)

    @Query(
        "Insert into tabs_table (name, onum) values( :name, 0 )"
    )
    fun addTabFirstTab(name: String)

    @Update
    suspend fun updateTab(tab: Tab)
    @Query(
        "UPDATE tabs_table set name = :name WHERE id = :id"
    )
    suspend fun updateTabNative(id: Int, name: String)

    @Query(
        "UPDATE tabs_table set onum = :onum WHERE id = :id"
    )
    suspend fun updOnum(id: Int, onum: Int)

    @Query(
        "UPDATE tabs_table set onum = onum - 1 WHERE id = :id"
    )
    suspend fun upTab(id: Int)

    @Query(
        "UPDATE tabs_table set onum = onum + 1 WHERE id = :id"
    )
    suspend fun downTab(id: Int)

}