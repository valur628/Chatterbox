package com.pjcgnu.chatterbox.Databases;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pjcgnu.chatterbox.Databases.Converters.DateConverters;


@Database(entities = {com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity.class,
        com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract com.pjcgnu.chatterbox.Databases.DaoClass.BookDatasDAO getBookDatasDao();
    public abstract com.pjcgnu.chatterbox.Databases.DaoClass.ReadingDatasDAO getReadingDatasDao();
    public static final int NUMBER_OF_THREADS = 4;
    private static volatile UserRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                UserRoomDatabase.class, "user_database")
                        .allowMainThreadQueries() // modified
                        .build();
            }
        }
        return INSTANCE;
    }
}