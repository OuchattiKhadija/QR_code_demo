package net.maribat.qrtoscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    public final static int QRCodeWidth = 500;
    Bitmap bitmap;

    Button sendData_btn, generateDr_btn, saveQr_btn;
    TextInputEditText firstName_ed, lastName_ed, age_ed;
    LinearLayout layout_btns_code;
    ImageView codeQr_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendData_btn = findViewById(R.id.send_data_to_db);
        generateDr_btn = findViewById(R.id.generate_qr_code);
        saveQr_btn = findViewById(R.id.save_qr_code);
        firstName_ed = findViewById(R.id.prenom_edit);
        lastName_ed = findViewById(R.id.nom_edit);
        age_ed = findViewById(R.id.age_edit);
        layout_btns_code = findViewById(R.id.qr_code_linear_layout);
        codeQr_img = findViewById(R.id.qr_code_image);

        sendData_btn.setOnClickListener(view -> {
            layout_btns_code.setVisibility(View.VISIBLE);
        });

        generateDr_btn.setOnClickListener(view -> {
            if (firstName_ed.getText().toString().trim().length() == 0) {
                Toast.makeText(MainActivity.this, "Enter Text", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    bitmap = textToImageEncode(firstName_ed.getText().toString());
                    codeQr_img.setVisibility(View.VISIBLE);
                    codeQr_img.setImageBitmap(bitmap);
                    saveQr_btn.setVisibility(View.VISIBLE);
                    saveQr_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner"
                                    , null);
                            Toast.makeText(MainActivity.this, "Saved to galary", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.go_to_scan:
                //do something
                i = new Intent(MainActivity.this, ScanQRCodeActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}