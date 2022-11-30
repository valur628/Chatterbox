package com.pjcgnu.chatterbox.Databases.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "BookDatas"/*,
        foreignKeys = @ForeignKey
                (entity = MeasureRoundsEntity.class,
                parentColumns = "MeasureRoundStartTime",
                childColumns = "MeasureRoundStartTimeFK")*/
        //그냥 변수로 저장해놓았다가 하기
)
public class BookDatasEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int BookDataID;

    @ColumnInfo
    private String BookDataName;

    @ColumnInfo
    private String BookDataImageAddress;

    @ColumnInfo
    private String BookDataWriter;

    @ColumnInfo
    private String BookDataContent;

    @ColumnInfo
    private float BookDataRating;

    public BookDatasEntity() {
    }

    public BookDatasEntity(int DB_BookDataID, String DB_BookDataName, String DB_BookDataImageAddress, String DB_BookDataWriter, String DB_BookDataContent, float DB_BookDataRating) {
        this.BookDataID = DB_BookDataID;
        this.BookDataName = DB_BookDataName;
        this.BookDataImageAddress = DB_BookDataImageAddress;
        this.BookDataWriter = DB_BookDataWriter;
        this.BookDataContent = DB_BookDataContent;
        this.BookDataRating = DB_BookDataRating;
    }

    public int getBookDataID() {
        return BookDataID;
    }
    public void setBookDataID(int BookDataID) {
        this.BookDataID = BookDataID;
    }

    public String getBookDataName() { return BookDataName; }
    public void setBookDataName(String BookDataName) {
        this.BookDataName = BookDataName;
    }

    public String getBookDataImageAddress() { return BookDataImageAddress; }
    public void setBookDataImageAddress(String BookDataImageAddress) {
        this.BookDataImageAddress = BookDataImageAddress;
    }

    public String getBookDataWriter() { return BookDataWriter; }
    public void setBookDataWriter(String BookDataWriter) {
        this.BookDataWriter = BookDataWriter;
    }

    public String getBookDataContent() { return BookDataContent; }
    public void setBookDataContent(String BookDataContent) {
        this.BookDataContent = BookDataContent;
    }

    public float getBookDataRating() { return BookDataRating; }
    public void setBookDataRating(float BookDataRating) {
        this.BookDataRating = BookDataRating;
    }
}