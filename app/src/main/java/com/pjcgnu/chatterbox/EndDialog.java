package com.pjcgnu.chatterbox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class EndDialog extends Dialog {
    private TextView txt_contents;
    private Button shutdownClick;
    private String StartTimeStr;
    private String EndTimeStr;
    private String TotalTimeStr;
    private long TotalTimeTemp;
    long hour, min, sec;

    public EndDialog(@NonNull Context context, LocalDateTime StartTime, LocalDateTime EndTime) {
        super(context);
        setContentView(R.layout.activity_end_dialog);

        StartTimeStr = StartTime.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초"));
        EndTimeStr = EndTime.format(DateTimeFormatter.ofPattern("HH시 mm분 ss초"));
        TotalTimeTemp = ChronoUnit.SECONDS.between(StartTime, EndTime);

        min = TotalTimeTemp / 60;
        hour = min / 60;
        sec = TotalTimeTemp % 60;
        min = min % 60;

        //시간 수정

        txt_contents = findViewById(R.id.txt_contents);
        txt_contents.setText("\n시작 시간: " + StartTimeStr + "\n\n종료 시간: " + EndTimeStr+
                "\n\n\n총 소요 시간: " + hour + "시 " + min + "분 " + sec + "초");
        shutdownClick = findViewById(R.id.btn_shutdown);
        shutdownClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechTextActivity.endNowCheck = true;
                dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

    }
}