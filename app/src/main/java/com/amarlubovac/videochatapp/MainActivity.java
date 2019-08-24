package com.amarlubovac.videochatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LinearLayout urlView;
    ProgressBar progressBar;
    StreamData streamData;
    TextView customerTV;
    TextView agentTV;
    Button generateUrlsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlView = findViewById(R.id.urlView);
        progressBar = findViewById(R.id.progressBar);
        customerTV = findViewById(R.id.customerUrlTV);
        agentTV = findViewById(R.id.agentUrlTV);
        generateUrlsBtn = findViewById(R.id.buttonGenerate);

        generateUrlsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerStream();
            }
        });

        customerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(customerTV.getText().toString()));
                startActivity(browserIntent);
            }
        });

        agentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(agentTV.getText().toString()));
                startActivity(browserIntent);
            }
        });

        customerTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("text", customerTV.getText().toString());
                cManager.setPrimaryClip(cData);
                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        agentTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("text", agentTV.getText().toString());
                cManager.setPrimaryClip(cData);
                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void registerStream() {
        progressBar.setVisibility(View.VISIBLE);
        urlView.setVisibility(View.GONE);
        ApiService apiService = new RetrofitFactory().makeApiService();
        Call<StreamData> call = apiService.registerStream();

        call.enqueue(new Callback<StreamData>() {
            @Override
            public void onResponse(Call<StreamData> call, Response<StreamData> response) {
                urlView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                streamData = response.body();
                showUrls();

            }

            @Override
            public void onFailure(Call<StreamData> call, Throwable t) {
                urlView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUrls() {
        String customerUrl = "https://video-test.cbfsident.com/?role=customer&userName=Donald%20Duck&streamId=" + streamData.streamId + "&token=" + streamData.customerToken;
        customerTV.setText(customerUrl);

        String agentUrl = "https://video-test.cbfsident.com/?role=operator&userName=Mickey%20Mouse&streamId=" + streamData.streamId + "&token=" + streamData.operatorToken;
        agentTV.setText(agentUrl);
    }
}
