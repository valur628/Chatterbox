package com.pjcgnu.chatterbox.Databases.DaoClass;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReadingDatasDAO {
    @Insert
    void insert(com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity measurementTableEntity);

    @Update
    void update(com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity measurementTableEntity);

    @Delete
    void delete(com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity measurementTableEntity);

    //Select All Data
    @Query("SELECT * FROM ReadingDatas")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity> getAllData();

    @Query("DELETE FROM ReadingDatas")
    void deleteAll();
}