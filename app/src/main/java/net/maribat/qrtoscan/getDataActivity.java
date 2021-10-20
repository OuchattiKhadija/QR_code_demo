package net.maribat.qrtoscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class getDataActivity extends AppCompatActivity {
    private static final String TAG = "GetDataActivity";
    TextView first,last,age,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        first = findViewById(R.id.firstname);
        last = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        id = findViewById(R.id.id);

        Intent intent = getIntent();
        String firstName_str = intent.getStringExtra("FIRST_NAME");
        String lastName_str = intent.getStringExtra("LAST_NAME");
        String age_str = intent.getStringExtra("AGE_");
        String id_str = intent.getStringExtra("ID_");
        first.setText(firstName_str);
        last.setText(lastName_str);
        age.setText(age_str);
        id.setText(id_str);
        Log.i(TAG, "onCreate: "+ firstName_str+""+lastName_str+""+age_str);

    }

    public void gotoScan(View view) {
        Intent intent = new Intent(this,ScanQRCodeActivity.class);
        startActivity(intent);
        finish();
    }
}