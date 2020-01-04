package com.riyagayasen.easyaccordiondemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.riyagayasen.easyaccordion.AccordionView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        AccordionView accordionView = this.findViewById(R.id.test_accordion);
        TextView textView = accordionView.findViewById(R.id.textView);
        textView.setText(R.string.demo_text);
    }
}
