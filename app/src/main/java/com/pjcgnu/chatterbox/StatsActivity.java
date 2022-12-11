package com.pjcgnu.chatterbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity;
import com.pjcgnu.chatterbox.Databases.UserRoomDatabase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private ArrayList<StatsModel> arrayList;
    private StatsAdapter statsAdapter;
    private RecyclerView recyclerView;

    private String StartTimeStr;
    private String EndTimeStr;
    private String TotalTimeStr;
    private long TotalTimeTemp;
    long hour, min, sec;

    int bookReadingDataID;
    LocalDateTime bookReadingDataStartTime;
    LocalDateTime bookReadingDataEndTime;
    String bookBookNameFK;

    List<ReadingDatasEntity> ReadingDB;
    ArrayList<StatsModel> arrayStatsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        this.InitializeReadingData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final StatsAdapter statsAdapter = new StatsAdapter(this, arrayStatsModel);

        ReadingDB = UserRoomDatabase.getDatabase(getApplicationContext()).getReadingDatasDao().getAllData();
        ReadingDatasEntity readingDatasEntity = new ReadingDatasEntity();

        arrayList = new ArrayList<>();
        listView.setAdapter(statsAdapter);

    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);	//intent 에 명시된 액티비티로 이동
        finish();
    }
    private void getReadingDB(int counter){
        bookReadingDataID = ReadingDB.get(counter).getReadingDataID();
        bookReadingDataStartTime = ReadingDB.get(counter).getReadingDataStartTime();
        bookReadingDataEndTime = ReadingDB.get(counter).getReadingDataEndTime();
        bookBookNameFK = ReadingDB.get(counter).getBookDataNameFK();
    }

    private String StartTimeTemp;
    private String EndTimeTemp;
    private String BookNameTmp;

    public void InitializeReadingData()
    {
        arrayStatsModel = new ArrayList<StatsModel>();
        ReadingDB = UserRoomDatabase.getDatabase(getApplicationContext()).getReadingDatasDao().getAllData();
        for(int i = 0 ; i<ReadingDB.size(); i++)
        {
            getReadingDB(i);
            StartTimeTemp = bookReadingDataStartTime.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초")) + "";
            EndTimeTemp = bookReadingDataEndTime.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초")) + "";
            TotalTimeTemp = ChronoUnit.SECONDS.between(bookReadingDataStartTime, bookReadingDataEndTime);

            min = TotalTimeTemp / 60;
            hour = min / 60;
            sec = TotalTimeTemp % 60;
            min = min % 60;
            TotalTimeStr = "총 소요 시간: " + hour + "시 " + min + "분 " + sec + "초";

            BookNameTmp = "읽은 책: " + bookBookNameFK;
            arrayStatsModel.add(new StatsModel(bookReadingDataID, StartTimeTemp, EndTimeTemp, TotalTimeStr, BookNameTmp));
        }
    }
}
