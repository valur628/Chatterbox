package com.pjcgnu.chatterbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity;
import com.pjcgnu.chatterbox.Databases.UserRoomDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button stButton=findViewById(R.id.speechtextButton);
        stButton.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),SpeechTextActivity.class);
            startActivity(intent);
        });
        Button dbResetButton=findViewById(R.id.DatabaseResetButton);
        dbResetButton.setOnClickListener(view -> {
            resetBook();
        });
    }

    List<BookDatasEntity> BookDB;
    int[] bookDataID = new int[3];
    String[] bookDataName = new String[3];
    String[] bookDataImageAddress = new String[3];
    String[] bookDataWriter = new String[3];
    String[] bookDataContent = new String[3];
    float[] bookDataRating = new float[3];

    String bookContent;

    private void resetBook() { //책 초기화
        UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().deleteAll();
        BookDB = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();

        bookDataID[0] = 0;
        bookDataName[0] = "짱구는 못말려";
        bookDataImageAddress[0] = "N/A";
        bookDataWriter[0] = "우스이 요시토";
        loadBook(0);
        bookDataContent[0] = bookContent;
        bookDataRating[0] = 0;

        bookDataID[1] = 1;
        bookDataName[1] = "자연의 마음";
        bookDataImageAddress[1] = "N/A";
        bookDataWriter[1] = "장정심";
        loadBook(1);
        bookDataContent[1] = bookContent;
        bookDataRating[1] = 0;

        bookDataID[2] = 2;
        bookDataName[2] = "그를 보내며";
        bookDataImageAddress[2] = "N/A";
        bookDataWriter[2] = "한용운";
        loadBook(2);
        bookDataContent[2] = bookContent;
        bookDataRating[2] = 0;

        BookDatasEntity bookDatasEntity = new BookDatasEntity();
        for(int i = 0;i<3;i++){
            //bookDatasEntity.setBookDataID(bookDataID[i]);
            bookDatasEntity.setBookDataName(bookDataName[i]);
            bookDatasEntity.setBookDataImageAddress(bookDataImageAddress[i]);
            bookDatasEntity.setBookDataWriter(bookDataWriter[i]);
            bookDatasEntity.setBookDataContent(bookDataContent[i]);
            bookDatasEntity.setBookDataRating(bookDataRating[i]);
            UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().insert(bookDatasEntity);
        }
        Toast.makeText(this, "책 데이터 리셋", Toast.LENGTH_SHORT).show();
    }
    public void loadBook(int selectbook){
        try {
            InputStream in = null;
            switch (selectbook) {
                case 0:
                    in = getResources().openRawResource(R.raw.book_shinchan);
                    break;
                case 1:
                    in = getResources().openRawResource(R.raw.book_heartofnature);
                    break;
                case 2:
                    in = getResources().openRawResource(R.raw.book_sendinghimaway);
                    break;
            }
            byte[] b = new byte[in.available()];

            in.read(b);
            bookContent = new String(b);
            Log.e("load book",bookContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}