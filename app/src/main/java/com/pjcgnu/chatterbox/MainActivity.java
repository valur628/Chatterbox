package com.pjcgnu.chatterbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity;
import com.pjcgnu.chatterbox.Databases.UserRoomDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<BookDatasEntity> BookDB_one;
    int bookDataID_one;
    String bookDataName_one;
    String bookDataImageAddress_one;
    String bookDataWriter_one;
    float bookDataRating_one;
    //TextView a;

    ArrayList<MainViewModel> arrayMainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetBook();

        //a = findViewById(R.id.textViewTest);
        Button dbResetButton=findViewById(R.id.DatabaseResetButton);
        dbResetButton.setOnClickListener(view -> {
            resetBook();
        });

        Button statsButton=findViewById(R.id.StatsButton);
        statsButton.setOnClickListener(view -> {
            Intent intent1=new Intent(getApplicationContext(),StatsActivity.class);
            startActivity(intent1);
        });

        this.InitializeBookData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this, arrayMainViewModel);

        BookDB_one = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();
        //a.setText(BookDB_one.size() + " ");

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            //a.setText("OI1");
            Intent intent=new Intent(getApplicationContext(),SpeechTextActivity.class);
            intent.putExtra("pos", position);
            Toast.makeText(getApplicationContext(),
                    myAdapter.getItem(position).getBookName() + " [" + position +"번]",
                    Toast.LENGTH_LONG).show();
            startActivity(intent);
        });
    }

    public void InitializeBookData()
    {
        arrayMainViewModel = new ArrayList<MainViewModel>();
        BookDB_one = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();
        //a.setText("IN1");
        for(int i = 0 ; i<BookDB_one.size(); i++)
        {
            //a.setText("IN2");
            getBookDB(i);
            if(bookDataImageAddress_one =="N/A") {
                resetBook();
                break;
            }
            arrayMainViewModel.add(new MainViewModel(bookDataName_one, bookDataImageAddress_one, bookDataWriter_one, bookDataRating_one));
        }
    }

    List<BookDatasEntity> BookDB;

    int DB_count = 7;

    int[] bookDataID = new int[DB_count];
    String[] bookDataName = new String[DB_count];
    String[] bookDataImageAddress = new String[DB_count];
    String[] bookDataWriter = new String[DB_count];
    String[] bookDataContent = new String[DB_count];
    float[] bookDataRating = new float[DB_count];

    String bookContent;

    private void resetBook() { //책 초기화
        UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().deleteAll();
        BookDB = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();

        bookDataID[0] = 0;
        bookDataName[0] = "짱구는 못말려";
        bookDataImageAddress[0] = Integer.toString(R.drawable.img_shinchan);
        bookDataWriter[0] = "우스이 요시토";
        loadBook(0);
        bookDataContent[0] = bookContent;
        bookDataRating[0] = 3;

        bookDataID[1] = 1;
        bookDataName[1] = "자연의 마음";
        bookDataImageAddress[1] = Integer.toString(R.drawable.img_heartofnature);
        bookDataWriter[1] = "장정심";
        loadBook(1);
        bookDataContent[1] = bookContent;
        bookDataRating[1] = 4;

        bookDataID[2] = 2;
        bookDataName[2] = "그를 보내며";
        bookDataImageAddress[2] = Integer.toString(R.drawable.img_sendinghimaway);
        bookDataWriter[2] = "한용운";
        loadBook(2);
        bookDataContent[2] = bookContent;
        bookDataRating[2] = 4;

        bookDataID[3] = 3;
        bookDataName[3] = "눈물을 마시는 새";
        bookDataImageAddress[3] = Integer.toString(R.drawable.img_btdb);
        bookDataWriter[3] = "이영도";
        loadBook(3);
        bookDataContent[3] = bookContent;
        bookDataRating[3] = 2;

        bookDataID[4] = 4;
        bookDataName[4] = "달";
        bookDataImageAddress[4] = Integer.toString(R.drawable.img_moon);
        bookDataWriter[4] = "인공지능";
        loadBook(4);
        bookDataContent[4] = bookContent;
        bookDataRating[4] = 2;

        bookDataID[5] = 5;
        bookDataName[5] = "비";
        bookDataImageAddress[5] = Integer.toString(R.drawable.img_rain);
        bookDataWriter[5] = "인공지능";
        loadBook(5);
        bookDataContent[5] = bookContent;
        bookDataRating[5] = 1;

        bookDataID[6] = 6;
        bookDataName[6] = "메밀꽃 필 무렵";
        bookDataImageAddress[6] = Integer.toString(R.drawable.img_wbfb);
        bookDataWriter[6] = "이효석";
        loadBook(6);
        bookDataContent[6] = bookContent;
        bookDataRating[6] = 5;

        BookDatasEntity bookDatasEntity = new BookDatasEntity();
        for(int i = 0;i<DB_count;i++){
            //bookDatasEntity.setBookDataID(bookDataID[i]);
            bookDatasEntity.setBookDataName(bookDataName[i]);
            bookDatasEntity.setBookDataImageAddress(bookDataImageAddress[i]);
            bookDatasEntity.setBookDataWriter(bookDataWriter[i]);
            bookDatasEntity.setBookDataContent(bookDataContent[i]);
            bookDatasEntity.setBookDataRating(bookDataRating[i]);
            UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().insert(bookDatasEntity);
        }
        //Toast.makeText(this, "책 데이터 리셋", Toast.LENGTH_SHORT).show();
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
                case 3:
                    in = getResources().openRawResource(R.raw.book_btdb);
                    break;
                case 4:
                    in = getResources().openRawResource(R.raw.book_moon);
                    break;
                case 5:
                    in = getResources().openRawResource(R.raw.book_rain);
                    break;
                case 6:
                    in = getResources().openRawResource(R.raw.book_wbfb);
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

    private void getBookDB(int counter){
        bookDataID_one = BookDB_one.get(counter).getBookDataID();
        bookDataName_one = BookDB_one.get(counter).getBookDataName();
        bookDataImageAddress_one = BookDB_one.get(counter).getBookDataImageAddress();
        bookDataWriter_one = BookDB_one.get(counter).getBookDataWriter();
        bookDataRating_one = BookDB_one.get(counter).getBookDataRating();
    }
}