package net.maribat.qrtoscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class getDataActivity extends AppCompatActivity {
TextView first,last,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        first = findViewById(R.id.firstname);
        last = findViewById(R.id.lastName);
        age = findViewById(R.id.age);

        Intent intent = getIntent();
        String firstName_str = intent.getStringExtra("FIRST_NAME");
        String lastName_str = intent.getStringExtra("LAST_NAME");
        String age_str = intent.getStringExtra("AGE");
        first.setText(firstName_str);
        last.setText(lastName_str);
        age.setText(age_str);

    }
}