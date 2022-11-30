package com.pjcgnu.chatterbox.Databases.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.*;
import java.time.LocalDateTime;


@Entity(tableName = "ReadingDatas")
public class ReadingDatasEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ReadingDataID;

    @ColumnInfo
    private LocalDateTime ReadingDataStartTime;

    @ColumnInfo
    private LocalDateTime ReadingDataEndTime;

    @ColumnInfo
    private String BookDataNameFK;

    public ReadingDatasEntity() {
    }

    public ReadingDatasEntity(int DB_ReadingDataID, LocalDateTime DB_ReadingDataStartTime, LocalDateTime DB_ReadingDataEndTime) {
        this.ReadingDataID = DB_ReadingDataID;
        this.ReadingDataStartTime = DB_ReadingDataStartTime;
        this.ReadingDataEndTime = DB_ReadingDataEndTime;
    }

    public int getReadingDataID() {
        return ReadingDataID;
    }
    public void setReadingDataID(int MeasureRoundID) {
        this.ReadingDataID = MeasureRoundID;
    }

    public LocalDateTime getReadingDataStartTime() { return ReadingDataStartTime; }
    public void setReadingDataStartTime(LocalDateTime ReadingDataStartTime) {
        this.ReadingDataStartTime = ReadingDataStartTime;
    }

    public LocalDateTime getReadingDataEndTime() { return ReadingDataEndTime; }
    public void setReadingDataEndTime(LocalDateTime ReadingDataEndTime) {
        this.ReadingDataEndTime = ReadingDataEndTime;
    }

    public String getBookDataNameFK() { return BookDataNameFK; }
    public void setBookDataNameFK(String BookDataNameFK) {
        this.BookDataNameFK = BookDataNameFK;
    }
}