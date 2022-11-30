package com.pjcgnu.chatterbox.Databases.DaoClass;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface BookDatasDAO {
    @Insert
    void insert(com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity measurementTableEntity);

    @Update
    void update(com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity measurementTableEntity);

    @Delete
    void delete(com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity measurementTableEntity);

    //Select All Data
    @Query("SELECT * FROM BookDatas")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getAllData();

    @Query("DELETE FROM BookDatas")
    void deleteAll();

    @Query("SELECT * FROM BookDatas WHERE BookDataName = :BookDataNames")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getBookNameData(String BookDataNames);

    @Query("SELECT * FROM BookDatas WHERE BookDataImageAddress = :BookDataImageAddresses")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getBookAddressData(String BookDataImageAddresses);

    @Query("SELECT * FROM BookDatas WHERE BookDataWriter = :BookDataWriters")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getBookWriterData(String BookDataWriters);

    @Query("SELECT * FROM BookDatas WHERE BookDataContent = :BookDataContents")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getBookContentData(String BookDataContents);

    @Query("SELECT * FROM BookDatas WHERE BookDataRating = :BookDataRatings")
    List<com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity> getBookRatingData(String BookDataRatings);
}