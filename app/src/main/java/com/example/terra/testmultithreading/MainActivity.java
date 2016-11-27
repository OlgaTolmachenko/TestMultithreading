package com.example.terra.testmultithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "TestHandler";

    private Handler handler;
    private TextView info;
    Button btnStart;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.tvInfo);
        btnStart = (Button) findViewById(R.id.btnStart);
        Button btnTest = (Button) findViewById(R.id.btnTest);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        btnStart.setOnClickListener(this);
        btnTest.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String text = String.format(getResources().getString(R.string.handlerMessage), msg.what);
                info.setText(text);
                if (msg.what == 10) {
                    btnStart.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };
    }

    private void downloadFile() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.btnStart:

                btnStart.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                info.setText("");

                Thread downloadFileThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            downloadFile();
                            handler.sendEmptyMessage(i);
                        }
                    }
                });
                downloadFileThread.start();
                break;

            case R.id.btnTest:
                Log.d(LOG_TAG, "test");
                break;

            default:
                break;
        }
    }
}
