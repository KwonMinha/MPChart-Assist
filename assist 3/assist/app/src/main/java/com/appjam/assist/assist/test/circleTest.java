package com.appjam.assist.assist.test;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appjam.assist.assist.R;



public class circleTest extends AppCompatActivity {
    // circle progress bar
    private ProgressBar progBar;
    private TextView text;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;

    // 차트
    LinearLayout mainLayout;
    Resources res;
    Animation growAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_test);

        // 프로그래스
        progBar = (ProgressBar) findViewById(R.id.progressBar);
        text = (TextView) findViewById(R.id.tv_percent);
        dosomething();

        // 차트
        res = getResources();
        growAnim = AnimationUtils.loadAnimation(this, R.anim.chart_grow);
        mainLayout = (LinearLayout)findViewById(R.id.layout_chart);

        addItem("Red", 80, R.color.colorRed);
        addItem("Gray", 100, R.color.colorGray);
        addItem("Blue", 40, R.color.colorBlue);

    }

    public void dosomething() {
        new Thread(new Runnable() {
            public void run() {
                final int presentage = 0;
                while (mProgressStatus < 63) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progBar.setProgress(mProgressStatus);
                            text.setText("" + mProgressStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void addItem(String name, int value, int color) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 텍스트뷰 추가
        TextView textView = new TextView(this);
        textView.setText(name);
        params.width = 180;
        params.setMargins(0, 4, 0, 4);
        itemLayout.addView(textView, params);

        // 프로그레스바 추가
        ProgressBar proBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(100);
        if(color == R.color.colorRed)
            proBar.setProgressDrawable(
                    getResources().getDrawable(R.drawable.chart_skyblue));
        else if(color == R.color.colorGray)
            proBar.setProgressDrawable(
                    getResources().getDrawable(R.drawable.chart_gray));
        else if(color == R.color.colorBlue)
            proBar.setProgressDrawable(
                    getResources().getDrawable(R.drawable.chart_pink));

        proBar.setAnimation(growAnim);

        params2.height = 20;
        params2.width = value * 3;
        params2.gravity = Gravity.CENTER_VERTICAL;
        itemLayout.addView(proBar, params2);

        mainLayout.addView(itemLayout, params3);
    }
}
