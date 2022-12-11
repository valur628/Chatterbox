package com.pjcgnu.chatterbox;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.speech.v1.SpeechProto;
import com.pjcgnu.chatterbox.Databases.EntityClass.BookDatasEntity;
import com.pjcgnu.chatterbox.Databases.EntityClass.ReadingDatasEntity;
import com.pjcgnu.chatterbox.Databases.UserRoomDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class SpeechTextActivity extends AppCompatActivity implements MessageDialogFragment.Listener {

    private static final String TAG = "SpeechTextActivity";

    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static final String STATE_RESULTS = "results";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private SpeechService mSpeechService;

    private VoiceRecorder mVoiceRecorder;

    List<BookDatasEntity> BookDB;
    int bookDataID;
    String bookDataName;
    String bookDataContent;
    List<ReadingDatasEntity> ReadingDB;
    int readingDataID;
    LocalDateTime readingDataStartTime;
    LocalDateTime readingDataEndTime;

    LocalDateTime StartCheckTime = LocalDateTime.now();
    LocalDateTime EndCheckTime = LocalDateTime.now();

    String bookNameFK;
    boolean endEarlyCheck = false;
    public static boolean endNowCheck = false;

    private EndDialog endDialog;

    //DB 관련
    private int bookDBCounter = 0;
    private int readingDBCounter = 0;
    private int pageCounter = 0;
    private String[] arrayContent;

    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            showStatus(true);
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            showStatus(false);
            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }

    };

    // Resource caches
    private int mColorHearing;
    private int mColorNotHearing;

    // Text data
    private double textMatchRate;

    // View references
    private TextView mStatus;
    private TextView mText;
    private TextView mSave;
    private TextView mRead;
    private TextView mRate;
    private TextView mResult;
    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;

    int endCountCheck = 0;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
            mStatus.setVisibility(View.VISIBLE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_speechtext);

        final Resources resources = getResources();
        final Resources.Theme theme = getTheme();
        mColorHearing = ResourcesCompat.getColor(resources, R.color.status_hearing, theme);
        mColorNotHearing = ResourcesCompat.getColor(resources, R.color.status_not_hearing, theme);

        Intent intent = getIntent();
        bookDBCounter = intent.getIntExtra("pos", 0);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mStatus = (TextView) findViewById(R.id.status);
        mText = (TextView) findViewById(R.id.text_main);
        mSave = (TextView) findViewById(R.id.inSaveText);
        mRead = (TextView) findViewById(R.id.inReadText);
        mRate = (TextView) findViewById(R.id.rateText);
        mResult = (TextView) findViewById(R.id.resultText);

        BookDB = UserRoomDatabase.getDatabase(getApplicationContext()).getBookDatasDao().getAllData();
        ReadingDB = UserRoomDatabase.getDatabase(getApplicationContext()).getReadingDatasDao().getAllData();

        getBookDB(bookDBCounter);
        //getReadingDB(readingDBCounter);
        arrayContent = bookDataContent.split("___");
        mRead.setText(arrayContent[pageCounter]);
        mResult.setText("텍스트를 읽어주세요.");

        textMatchRate = TextMatching.similarity(
                "테스트용",
                arrayContent[pageCounter])*100;  //멀티코어 프로세싱 박아야할듯
        mRate.setText("일치율: " + Double.toString(textMatchRate) + "%");
        /*mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> results = savedInstanceState == null ? null :
                savedInstanceState.getStringArrayList(STATE_RESULTS);
        mAdapter = new ResultAdapter(results);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);

        // Start listening to voices
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecorder();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    protected void onStop() {
        try{
            // Stop listening to voice
            stopVoiceRecorder();

            // Stop Cloud Speech API
            mSpeechService.removeListener(mSpeechServiceListener);
            unbindService(mServiceConnection);
            mSpeechService = null;

            super.onStop();
        } catch(Exception e){
            Log.d("onstop", String.valueOf(e));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            outState.putStringArrayList(STATE_RESULTS, mAdapter.getResults());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (permissions.length == 1 && grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecorder();
            } else {
                showPermissionMessageDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_file:
                mSpeechService.recognizeInputStream(getResources().openRawResource(R.raw.audio));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    private void showPermissionMessageDialog() {
        MessageDialogFragment
                .newInstance(getString(R.string.permission_message))
                .show(getSupportFragmentManager(), FRAGMENT_MESSAGE_DIALOG);
    }

    private void showStatus(final boolean hearingVoice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatus.setTextColor(hearingVoice ? mColorHearing : mColorNotHearing);
            }
        });
    }

    @Override
    public void onMessageDialogDismissed() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private final SpeechService.Listener mSpeechServiceListener =
            new SpeechService.Listener() {
                @Override
                public void onSpeechRecognized(final String text, final boolean isFinal) {
                    if (isFinal) {
                        mVoiceRecorder.dismiss();
                    }
                    if (mText != null && !TextUtils.isEmpty(text)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinal) {
                                    if(endEarlyCheck == false) {
                                        mText.setText(null);
                                        mSave.setText(text);
                                        mRead.setText(arrayContent[pageCounter]);
                                        textMatchRate = TextMatching.similarity(
                                                arrayContent[pageCounter],
                                                text) * 100;  //멀티코어 프로세싱 박아야할듯
                                        mRate.setText("일치율: " + textMatchRate + "%");
                                    }
                                    if(textMatchRate>=70) {
                                        if(pageCounter < arrayContent.length);{
                                            pageCounter++;
                                        }
                                        if(endCountCheck <= 0) {
                                            if (endEarlyCheck == true) {
                                                endCountCheck++;
                                                EndCheckTime = LocalDateTime.now();
                                                setReadingDB();
                                                endDialog = new EndDialog(SpeechTextActivity.this, StartCheckTime, EndCheckTime);
                                                endDialog.show();
                                            } else {
                                                if (arrayContent[pageCounter].contains("===")) {
                                                    arrayContent[pageCounter] = arrayContent[pageCounter].replaceAll("===", "");
                                                    endEarlyCheck = true;
                                                }
                                                mRead.setText(arrayContent[pageCounter]);
                                                mResult.setText("정답입니다. 계속 읽어주세요.");
                                            }
                                        }
                                    }
                                    //mAdapter.addResult(text);
                                    //mRecyclerView.smoothScrollToPosition(0);
                                    else{
                                        mResult.setText("오답입니다. 다시 읽어주세요.");
                                    }
                                } else {
                                    if(endEarlyCheck == false) {
                                        mText.setText(text);
                                        mRead.setText(arrayContent[pageCounter]);
                                    }
                                }
                            }
                        });
                    }
                }
            };

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_result, parent, false));
            text = (TextView) itemView.findViewById(R.id.text_main);
        }

    }

    private static class ResultAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final ArrayList<String> mResults = new ArrayList<>();

        ResultAdapter(ArrayList<String> results) {
            if (results != null) {
                mResults.addAll(results);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(mResults.get(position));
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        void addResult(String result) {
            mResults.add(0, result);
            notifyItemInserted(0);
        }

        public ArrayList<String> getResults() {
            return mResults;
        }

    }

    public static void toErrorLog(String TAG) {
        Log.e(TAG, "Error Message");
        Log.i(TAG, "Error Line___("+lineOut()+")");

    }

    public static String lineOut() {
        int level = 4;
        StackTraceElement[] traces;
        traces = Thread.currentThread().getStackTrace();
        return (" at "  + traces[level] + " ");
    }

    private void getBookDB(int counter){
        bookDataID = BookDB.get(counter).getBookDataID();
        bookDataName = BookDB.get(counter).getBookDataName();
        bookDataContent = BookDB.get(counter).getBookDataContent();
    }

    private void getReadingDB(int counter){
        readingDataID = ReadingDB.get(counter).getReadingDataID();
        readingDataStartTime = ReadingDB.get(counter).getReadingDataStartTime();
        readingDataEndTime = ReadingDB.get(counter).getReadingDataEndTime();
        bookNameFK = ReadingDB.get(counter).getBookDataNameFK();
    }

    private void setReadingDB(){
        ReadingDatasEntity readingDatasEntity = new ReadingDatasEntity();

        readingDatasEntity.setReadingDataStartTime(StartCheckTime);
        readingDatasEntity.setReadingDataEndTime(EndCheckTime);
        readingDatasEntity.setBookDataNameFK(bookDataName);
        UserRoomDatabase.getDatabase(getApplicationContext()).getReadingDatasDao().insert(readingDatasEntity);
        Toast.makeText(this, "시간 저장", Toast.LENGTH_SHORT).show();
    }

}
