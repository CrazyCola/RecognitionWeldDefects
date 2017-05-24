package com.example.olechka.recognitionwelddefects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otchet);

        ListView report_list = (ListView) findViewById(R.id.report_list);
        report_list.setAdapter(new ReportListAdapter(this));


    }
}
