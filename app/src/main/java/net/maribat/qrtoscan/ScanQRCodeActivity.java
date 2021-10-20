package net.maribat.qrtoscan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanQRCodeActivity extends AppCompatActivity {
    Button scanNowBtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        scanNowBtn = findViewById(R.id.scan_now_btn);
        //Declaration Builder & Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qrscanner-mongodb-api.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //insert data into the Inerface
         apiInterface = retrofit.create(ApiInterface.class);
        scanNowBtn.setOnClickListener(view -> {
            scanCode();
        });
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scaning code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                /*  Toast.makeText(ScanQRCodeActivity.this,"getting data ...",Toast.LENGTH_SHORT).show();
                getData(result.getContents());*/
                getData(result.getContents());
             /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning result");
                Toast.makeText(ScanQRCodeActivity.this,"getting data ...",Toast.LENGTH_SHORT).show();

                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }else {
                Toast.makeText(ScanQRCodeActivity.this, "No result", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void getData(String idUser) {
        final User[] user = new User[1];
        Call<User> call = apiInterface.getUserById(idUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               // title_tv.setText(response.body().getTitle());
                if (response.body() != null) {
                    Log.i("TAG", "onResponse: " + response.body().getFirstName());
                    Log.i("TAG", "onResponse: " + response.body().getLastName());
                    Log.i("TAG", "onResponse: " + response.body().getAge());
                     user[0] = new User(response.body().getFirstName(),response.body().getLastName(),response.body().getAge());
                }else{
                    Toast.makeText(ScanQRCodeActivity.this,"No data",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
             //   title_tv.setText(t.getMessage());
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });

        Intent i = new Intent(ScanQRCodeActivity.this,getDataActivity.class);
        i.putExtra("FIRST_NAME",user[0].getFirstName());
        i.putExtra("LAST_NAME",user[0].getLastName());
        i.putExtra("AGE",user[0].getAge());
        startActivity(i);
    }
}