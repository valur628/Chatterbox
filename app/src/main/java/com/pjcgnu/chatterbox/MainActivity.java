package com.pjcgnu.chatterbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    TextView a;

    ArrayList<ListViewData> arrayListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = findViewById(R.id.textViewTest);
        Button dbResetButton=findViewById(R.id.DatabaseResetButton);
        dbResetButton.setOnClickListener(view -> {
            resetBook();
        });

        this.InitializeBookData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final MyAdapter myAdapter = new MyAdapter(this,arrayListView);

        BookDB_one = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();
        a.setText(BookDB_one.size() + " ");

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            a.setText("OI1");
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
        arrayListView = new ArrayList<ListViewData>();
        BookDB_one = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();
        a.setText("IN1");
        for(int i = 0 ; i<BookDB_one.size(); i++)
        {
            a.setText("IN2");
            getBookDB(i);
            if(bookDataImageAddress_one =="N/A") {
                resetBook();
                break;
            }
            arrayListView.add(new ListViewData(bookDataName_one, bookDataImageAddress_one, bookDataWriter_one, bookDataRating_one));
        }
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
        bookDataRating[1] = 3;

        bookDataID[2] = 2;
        bookDataName[2] = "그를 보내며";
        bookDataImageAddress[2] = Integer.toString(R.drawable.img_sendinghimaway);
        bookDataWriter[2] = "한용운";
        loadBook(2);
        bookDataContent[2] = bookContent;
        bookDataRating[2] = 3;

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

    private void getBookDB(int counter){
        bookDataID_one = BookDB_one.get(counter).getBookDataID();
        bookDataName_one = BookDB_one.get(counter).getBookDataName();
        bookDataImageAddress_one = BookDB_one.get(counter).getBookDataImageAddress();
        bookDataWriter_one = BookDB_one.get(counter).getBookDataWriter();
        bookDataRating_one = BookDB_one.get(counter).getBookDataRating();
    }
}