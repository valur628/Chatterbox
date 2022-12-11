package com.pjcgnu.chatterbox;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {
/*
    TextView result_txt;
    Button btn, btn_ok;
    RatingBar rating;

    Dialog dialog;

    float num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        result_txt = findViewById(R.id.result_txt);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 생성
                dialog = new Dialog(RatingActivity.this);
                // 다이얼로그 설정
                dialog.setCancelable(false);
                // 다이얼로그 화면 설정
                dialog.setContentView(R.layout.rating_dialog);

                rating = dialog.findViewById(R.id.rating);
                btn_ok = dialog.findViewById(R.id.btn_ok);

                // 레이팅 바 속성 -----------------------------
                // 1. 레이팅 바에 기본값 채우기
                // rating.setRating(3);

                // 2. 사용자가 임의로 별점을 바꿀수 있도록 허가하는 메서드
                // true면 임의 변경 불가
                rating.setIsIndicator(false);

                // 3. 별 반칸당 점수 할당
                // 반칸당 0.5 점으로 할당
                rating.setStepSize(0.5f);
                // -------------------------------------------

                // 레이팅바의 변경사항을 감자히는 감지자
                rating.setOnRatingBarChangeListener(ratClick);

                // 다이얼로그 보이기
                dialog.show();

                btn_ok.setOnClickListener(click);
            }
        });
    }// onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), num + "점 주셨습니다.",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };
    RatingBar.OnRatingBarChangeListener ratClick = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float r, boolean b) {
            num = r;

            // 별 갯수에 따른 한칸의 점수
            float st = 10.0f / rating.getNumStars();

            // 소수점 한자리로 결과값 끊어주기
            String str = String.format("%.1f", st * r);

            result_txt.setText(str + "/10.0");
        }
    };*/
}